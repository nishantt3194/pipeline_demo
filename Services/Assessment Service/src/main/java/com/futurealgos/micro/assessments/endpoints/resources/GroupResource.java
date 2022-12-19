/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.endpoints.resources;

import com.futurealgos.micro.assessments.dto.payload.AssignGroup;
import com.futurealgos.micro.assessments.dto.payload.NewGroup;
import com.futurealgos.micro.assessments.dto.response.admin.GroupMinimal;
import com.futurealgos.micro.assessments.dto.response.admin.GroupSearch;
import com.futurealgos.micro.assessments.exceptions.BadRequestException;
import com.futurealgos.micro.assessments.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group/")
public class GroupResource {

    @Autowired
    GroupService groupService;

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody NewGroup group) {
        groupService.create(group, "");
        return ResponseEntity.accepted().body("New group Created Successfully");
    }


    @GetMapping("search")
    public List<GroupSearch> search(@RequestParam("partner") String partner) {
        return groupService.search(partner);
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<GroupMinimal>> list(@RequestParam("partner") String partner,
                                                   @RequestParam(value = "offset", required = false) Integer offset,
                                                   @RequestParam(value = "size", required = false) Integer size) {
//
//        System.out.println(groupService.list(Pageable.unpaged()));
//        if (paged)
//            return ResponseEntity.ok(groupService.list(PageRequest.of(offset, size)));

        return ResponseEntity.ok(groupService.list(Pageable.unpaged(), partner));
    }


    @PostMapping("assign")
    public ResponseEntity<String> assignGroup(@RequestBody AssignGroup payload, BindingResult result) {

        if (result.hasErrors()) {
            throw new BadRequestException("Invalid Payload Format");
        }
        groupService.assign(payload, "");
        return ResponseEntity.accepted().body(payload.getExaminees().size() > 1 ? "Examinees Assigned Successfully" : "Examinee Assigned Successfully");
    }


    @PutMapping("remove")
    public ResponseEntity<String> removeFromGroup(@RequestBody AssignGroup payload, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Invalid Payload Format");
        }
        groupService.remove(payload, "");
        return ResponseEntity.accepted().body(payload.getExaminees().size() > 1 ? "Examinees Removed Successfully" : "Examinee Removed Successfully");
    }


}
