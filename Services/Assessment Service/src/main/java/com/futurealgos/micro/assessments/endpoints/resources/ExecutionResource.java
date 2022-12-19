/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.endpoints.resources;

import com.futurealgos.micro.assessments.dto.payload.ExecAnswer;
import com.futurealgos.micro.assessments.dto.payload.ExecLogin;
import com.futurealgos.micro.assessments.dto.response.exec.SessionExchange;
import com.futurealgos.micro.assessments.dto.response.exec.TestExecData;
import com.futurealgos.micro.assessments.dto.response.login.LoginResponse;
import com.futurealgos.micro.assessments.dto.response.norms.PrerequisiteData;
import com.futurealgos.micro.assessments.dto.response.norms.RequiredDataMap;
import com.futurealgos.micro.assessments.exceptions.UnauthorizedException;
import com.futurealgos.micro.assessments.models.base.Assignee;
import com.futurealgos.micro.assessments.services.AssessmentService;
import com.futurealgos.micro.assessments.services.AssigneeService;
import com.futurealgos.micro.assessments.services.ExecutionService;
import com.futurealgos.micro.assessments.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * Package: com.futurealgos.micro.assessments.endpoints.resources
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@RestController
@RequestMapping("/exec/")
public class ExecutionResource {

    @Autowired
    AssessmentService assessmentService;
    @Autowired
    ExecutionService executionService;
    @Autowired
    AssigneeService assigneeService;

    @Autowired
    TokenService tokenService;

    @GetMapping("validate")
    public boolean checkValidity(@RequestParam(value = "ar_id") String id) {
        return executionService.checkValidity(id);
    }

    @GetMapping("login")
    public ExecLogin fetchLoginDetails(
            @RequestParam(value = "ar_id") String id,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "extn", required = false) String extn) {
        String uid = email != null && email.length() > 0 ? email : extn + "-" + phone;
        return assigneeService.fetchLoginDetails(id, uid);
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody ExecLogin payload) {
        return new LoginResponse(executionService.login(payload));
    }

    @GetMapping("norms")
    public Set<RequiredDataMap> fetchRequiredData(@RequestHeader(value = "Authorization") String header) {
        String token = header.replace("Bearer ", "");
        String assigneeId = tokenService.getClaim(token, "sub");
        Assignee assignee = assigneeService.fetch(assigneeId);
        boolean verification = tokenService.verify(token, assignee.getToken().getToken());
        if(!verification) {
            throw new UnauthorizedException("Access to data restricted. Invalid Token Provided");
        }

        return executionService.fetchRequiredData(assignee);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("norms")
    public void submitRequiredData(@RequestBody Set<RequiredDataMap> data, @RequestHeader(value = "Authorization") String header) {
        String token = header.replace("Bearer ", "");
        String assigneeId = tokenService.getClaim(token, "sub");
        Assignee assignee = assigneeService.fetch(assigneeId);
        boolean verification = tokenService.verify(token, assignee.getToken().getToken());
        if(!verification) {
            throw new UnauthorizedException("Access to data restricted. Invalid Token Provided");
        }

        assigneeService.mergeRequiredData(assignee, data);
    }

    @GetMapping("prerequisite")
    public PrerequisiteData fetchInstructions(@RequestHeader(value = "Authorization") String header) {
        String token = header.replace("Bearer ", "");
        String assigneeId = tokenService.getClaim(token, "sub");
        Assignee assignee = assigneeService.fetch(assigneeId);
        boolean verification = tokenService.verify(token, assignee.getToken().getToken());
        if(!verification) {
            throw new UnauthorizedException("Access to data restricted. Invalid Token Provided");
        }

        return executionService.fetchPrerequisiteData(assignee);
    }

    @GetMapping("start")
    public TestExecData initExec(@RequestHeader(value = "Authorization") String header) {
        String token = header.replace("Bearer ", "");
        String assigneeId = tokenService.getClaim(token, "sub");
        Assignee assignee = assigneeService.fetch(assigneeId);
        boolean verification = tokenService.verify(token, assignee.getToken().getToken());
        if(!verification) {
            throw new UnauthorizedException("Access to data restricted. Invalid Token Provided");
        }

        return executionService.startTest(assignee);
    }

    @PutMapping("sections/{action}")
    public SessionExchange actOnSection(@RequestHeader(value = "Authorization") String header,
                                        @PathVariable("action") String action, @RequestBody String id) {
        String token = header.replace("Bearer ", "");
        String assigneeId = tokenService.getClaim(token, "sub");
        Assignee assignee = assigneeService.fetch(assigneeId);
        boolean verification = tokenService.verify(token, assignee.getToken().getToken());
        if(!verification) {
            throw new UnauthorizedException("Access to data restricted. Invalid Token Provided");
        }

        if(action.equals("start")) {
            return executionService.startSection(assignee, id);
        } else if(action.equals("end")) {
            return executionService.endSection(assignee, id);
        }
        else throw new IllegalArgumentException("Invalid action");
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("answers")
    public Map<String, Integer> updateAnswer(@RequestHeader(value = "Authorization") String header, @RequestBody ExecAnswer payload) {
        String token = header.replace("Bearer ", "");
        String assigneeId = tokenService.getClaim(token, "sub");
        Assignee assignee = assigneeService.fetch(assigneeId);
        boolean verification = tokenService.verify(token, assignee.getToken().getToken());
        if(!verification) {
            throw new UnauthorizedException("Access to data restricted. Invalid Token Provided");
        }

        String sheetId = tokenService.getClaim(token, "aid");
        return assigneeService.updateSheet(payload, sheetId).getAnswers();
    }

    @PostMapping("submit")
    public void submit(@RequestHeader(value = "Authorization") String header) {
        String token = header.replace("Bearer ", "");
        String assigneeId = tokenService.getClaim(token, "sub");
        Assignee assignee = assigneeService.fetch(assigneeId);
        boolean verification = tokenService.verify(token, assignee.getToken().getToken());
        if(!verification) {
            throw new UnauthorizedException("Access to data restricted. Invalid Token Provided");
        }

        executionService.submit(assignee);
    }
}
