package com.futurealgos.admin.services.administration;

import com.futurealgos.admin.dto.payload.shifts.EditShift;
import com.futurealgos.admin.dto.payload.shifts.NewShift;
import com.futurealgos.admin.dto.response.shifts.ShiftInfo;
import com.futurealgos.admin.dto.response.shifts.ShiftMinimal;
import com.futurealgos.admin.dto.response.shifts.ShiftSearch;
import com.futurealgos.admin.exception.exceptions.AlreadyExistsException;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.Shift;
import com.futurealgos.admin.repos.primary.EntryRepository;
import com.futurealgos.admin.repos.primary.ShiftRepository;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.entry.EntryService;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.utils.constants.ConstantService;
import com.futurealgos.data.service.ServiceTemplate;
import com.futurealgos.specs.utils.PayloadConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShiftService extends ServiceTemplate<Shift, UUID, ShiftInfo, ShiftMinimal, ShiftSearch, NewShift, EditShift> {


    @Autowired
    MachineService machineService;
    @Autowired
    EntryService entryService;
    @Autowired
    ConstantService constantService;
    @Autowired
    EntryRepository entryRepository;

    ShiftRepository shiftRepository;
    AreaService areaService;

    public ShiftService(ShiftRepository shiftRepository, AreaService areaService) {
        super(Shift.class, shiftRepository, shiftRepository);
        this.shiftRepository = shiftRepository;
        this.areaService = areaService;
        super.setConverter(new ShiftService.ShiftConverter(areaService));
    }

    public List<ShiftSearch> getShiftNames(String  machineId, String dateVal,String areaId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateVal, df);
        List<ShiftSearch> shift = shiftRepository.findPendingShift(UUID.fromString(machineId),date ,UUID.fromString(areaId)).stream().map(Shift::search).toList();
        return  shift;
    }

    public static class ShiftConverter implements PayloadConverter<NewShift, Shift> {

        ShiftRepository shiftRepository;
        AreaService areaService;

        public ShiftConverter(AreaService areaService) {
            this.areaService = areaService;
        }

        @Override
        public Shift convert(NewShift newShift) {
            Area area = areaService.fetch(newShift.area());
            Shift shift= shiftRepository.findShiftByNameAndArea(newShift.name(),area).orElse(null);
            if (shift==null) {
                return Shift.builder()
                        .startTime(LocalTime.parse(newShift.startTime()))
                        .stopTime(LocalTime.parse(newShift.stopTime()))
                        .name(newShift.name())
                        .area(area)
                        .build();
            }
            else{
                throw new AlreadyExistsException("Shift already exists with name "+ newShift.name());
            }
        }
    }

    @Override
    public ShiftInfo update(String id, EditShift payload, String admin) {
        Shift shift = fetch(id);
        shift.setStopTime(LocalTime.parse(payload.stopTime()));
        shift.setStartTime(LocalTime.parse(payload.startTime()));
//        shift.setStatus(payload.status());
        return shiftRepository.save(shift, admin).info();
    }

    public void removeShift(String id){
        shiftRepository.delete(fetch(id));
    }
}
