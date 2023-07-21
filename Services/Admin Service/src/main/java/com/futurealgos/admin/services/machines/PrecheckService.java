package com.futurealgos.admin.services.machines;

import com.futurealgos.admin.dto.payload.precheck.NewPrecheck;
import com.futurealgos.admin.dto.response.precheck.PrecheckInfo;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.Reason;
import com.futurealgos.admin.models.secondary.machine.Precheck;
import com.futurealgos.admin.repos.secondary.PrecheckRepository;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.services.reasons.ReasonService;
import com.futurealgos.data.service.ServiceTemplate;
import com.futurealgos.specs.utils.PayloadConverter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrecheckService extends ServiceTemplate<Precheck, UUID, PrecheckInfo, Void, PrecheckInfo, NewPrecheck, NewPrecheck> {

    PrecheckRepository precheckRepo;

    MachineService machineService;

    ReasonService reasonService;

    public PrecheckService(PrecheckRepository precheckRepo, MachineService machineService, ReasonService reasonService) {
        super(Precheck.class, precheckRepo, precheckRepo);
        this.precheckRepo = precheckRepo;
        this.machineService = machineService;
        this.reasonService = reasonService;
        this.setConverter(new PreCheckConverter(machineService, reasonService));
        this.setPopulator(NewPrecheck::populate);
    }

    public static class PreCheckConverter implements PayloadConverter<NewPrecheck, Precheck> {

        MachineService machineService;
        ReasonService reasonService;

        public PreCheckConverter(MachineService machineService, ReasonService reasonService) {
            this.machineService = machineService;
            this.reasonService = reasonService;
        }


        @Override
        public Precheck convert(NewPrecheck newPrecheck) {
            Reason reason = reasonService.fetch(newPrecheck.reason());
            Machine machine = machineService.fetch(newPrecheck.machine());
            return Precheck.builder()
                    .reason(reason)
                    .machine(machine)
                    .time(newPrecheck.time())
                    .build();
        }

    }

    @Override
    public PrecheckInfo update(String id, NewPrecheck payload, String admin) {
        Precheck precheck = fetch(id);
        Reason reason = reasonService.fetch(payload.reason());
        precheck.setTime(payload.time());
        precheck.setReason(reason);
        return precheckRepo.save(precheck).info();
    }

    public void deletePrecheck(String id){
        Precheck precheck = fetch(id);
        precheckRepo.delete(precheck);
    }
}
