package com.futurealgos.admin.services.reasons;

import com.futurealgos.admin.dto.payload.NewReason;
import com.futurealgos.admin.dto.response.downtime.NewStationSpecificReason;
import com.futurealgos.admin.dto.response.downtime.ReasonSearch;
import com.futurealgos.admin.models.primary.Category;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.Reason;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.admin.models.secondary.reason.StationSpecificReason;
import com.futurealgos.admin.repos.secondary.StationSpecificReasonRepository;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.services.machines.StationService;
import com.futurealgos.admin.utils.enums.BreakdownType;
import com.futurealgos.data.service.ServiceTemplate;
import com.futurealgos.specs.utils.PayloadConverter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StationSpecificReasonService extends ServiceTemplate<StationSpecificReason, UUID, Void, Void, ReasonSearch, NewStationSpecificReason, Void> {
   
    StationSpecificReasonRepository repository;

    MachineService machineService;
    StationService stationService;
    ReasonService reasonService;

    public StationSpecificReasonService(StationSpecificReasonRepository repository,
                                        ReasonService reasonService,
                                        MachineService machineService,
                                        StationService stationService) {
        super(StationSpecificReason.class, repository, repository);

        this.machineService= machineService;
        this.reasonService = reasonService;
        this.stationService = stationService;
        this.repository = repository;
        super.setConverter(new StationSpecificReasonService.StationReasonConverter(machineService, stationService,reasonService));
    }

    public static class StationReasonConverter implements PayloadConverter<NewStationSpecificReason, StationSpecificReason> {

        MachineService machineService;
        StationService stationService;
        ReasonService reasonService;

        public StationReasonConverter(MachineService machineService,
                                      StationService stationService ,
                                      ReasonService reasonService) {
            this.machineService = machineService;
            this.stationService = stationService;
            this.reasonService = reasonService;
        }
        @Override
        public StationSpecificReason convert(NewStationSpecificReason newStationSpecificReason) {
            Machine machine= machineService.fetch(newStationSpecificReason.machine());
            Station station = stationService.fetch(newStationSpecificReason.station());
            Reason reason = reasonService.fetch(newStationSpecificReason.reason());
            return StationSpecificReason.builder()
                    .station(station)
                    .reason(reason)
                    .machine(machine)
                    .build();
        }
    }
}
