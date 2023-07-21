package com.futurealgos.admin.services.reasons;

import com.futurealgos.admin.dto.payload.NewReason;
import com.futurealgos.admin.dto.payload.shifts.NewShift;
import com.futurealgos.admin.dto.payload.shifts.UpdateReason;
import com.futurealgos.admin.dto.response.downtime.ReasonMinimal;
import com.futurealgos.admin.dto.response.downtime.ReasonSearch;
import com.futurealgos.admin.models.primary.*;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.admin.models.secondary.reason.StationSpecificReason;
import com.futurealgos.admin.repos.primary.ReasonRepository;
import com.futurealgos.admin.repos.secondary.StationSpecificReasonRepository;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.utils.enums.BreakdownType;
import com.futurealgos.data.service.ServiceTemplate;
import com.futurealgos.specs.utils.PayloadConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReasonService extends ServiceTemplate<Reason, UUID, Void, ReasonMinimal, ReasonSearch, NewReason, Void> {

    ReasonRepository reasonRepository;

    CategoryService categoryService;

    @Autowired
    MachineService machineService;
    @Autowired
    StationSpecificReasonRepository reasonLinkRepo;


    public ReasonService(ReasonRepository reasonRepository, CategoryService categoryService) {
        super(Reason.class, reasonRepository, reasonRepository);
        this.reasonRepository = reasonRepository;
        this.categoryService = categoryService;
        super.setConverter(new ReasonService.ReasonConverter(categoryService));

    }
    public static class ReasonConverter implements PayloadConverter<NewReason, Reason> {


        CategoryService categoryService;

        public ReasonConverter(CategoryService categoryService) {
            this.categoryService = categoryService;
        }
        @Override
        public Reason convert(NewReason reason) {
            Category category = categoryService.fetch(reason.defaultCategory());
            return Reason.builder()
                    .reason(reason.reason())
                    .type(BreakdownType.valueOf(reason.type()))
                    .defaultCategory(category)
                    .build();

        }
    }

    public Reason findByReason(String reason) {
        return reasonRepository.findByReasonIgnoreCase(reason);
    }


    public void updateStatus(List<UpdateReason> payloads){
        List<Reason> reasons = new ArrayList<>();
        for (UpdateReason payload : payloads)
        {
            Reason reason = fetch(payload.id());
            reason.setStatus(payload.status());
            reasons.add(reason);
        }

        reasonRepository.saveAll(reasons);

    }
}
