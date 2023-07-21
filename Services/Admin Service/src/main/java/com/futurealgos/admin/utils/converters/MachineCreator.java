package com.futurealgos.admin.utils.converters;

import com.futurealgos.admin.dto.payload.machines.NewMachine;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.repos.secondary.StationRepository;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.administration.UserService;
import com.futurealgos.specs.utils.PayloadConverter;

public class MachineCreator implements PayloadConverter<NewMachine, Machine> {

    AreaService areaService;

    UserService userService;

    StationRepository stationRepository;


    public MachineCreator(AreaService areaService) {
        this.areaService = areaService;

    }


    @Override
    public Machine convert(NewMachine newMachine) {
        Area area = areaService.fetch(newMachine.area());

        return Machine.builder()
                .name(newMachine.name())
                .speed(newMachine.speed())
                .prodLimit(newMachine.prodLimit())
                .lsaTarget(newMachine.lsa())
                .tolerance(newMachine.tolerance())
                .stretchedTarget(newMachine.stretched())
                //TODO:ADD THESE
                .stations(null)
                .area(area).build();
    }


}
