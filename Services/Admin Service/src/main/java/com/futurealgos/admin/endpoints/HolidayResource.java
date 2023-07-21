package com.futurealgos.admin.endpoints;


import com.futurealgos.admin.dto.payload.holiday.NewHolidayPayload;
import com.futurealgos.admin.dto.response.holiday.HolidayInfo;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.services.holiday.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holidays/")
public class HolidayResource {

    @Autowired
    HolidayService holidayService;

    @PostMapping("create")
    public String create(@RequestBody NewHolidayPayload payload, @AuthenticationPrincipal User user){
        holidayService.create(payload,user.getId().toString());
        return "Holiday created successfully";
    }

    @GetMapping("list")
    public Page<HolidayInfo> list(@RequestParam Integer offset, @RequestParam Integer size){
        return holidayService.list(offset,size);
    }
}
