package com.futurealgos.admin.services.holiday;

import com.futurealgos.admin.dto.payload.holiday.NewHolidayPayload;
import com.futurealgos.admin.dto.response.holiday.HolidayInfo;
import com.futurealgos.admin.models.primary.Holiday;
import com.futurealgos.admin.repos.secondary.HolidaysRepository;
import com.futurealgos.data.service.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class HolidayService extends ServiceTemplate<Holiday, UUID, HolidayInfo,HolidayInfo, Void, NewHolidayPayload,NewHolidayPayload> {

    @Autowired
    HolidaysRepository holidaysRepository;

    public HolidayService(HolidaysRepository repository) {
        super(Holiday.class,repository,repository);
        this.holidaysRepository=repository;
        this.converter=NewHolidayPayload::convert;
    }

    public long countByDateBetween(LocalDate startDate, LocalDate endDate) {
        return holidaysRepository.countByDateBetween(startDate,endDate);
    }
}
