/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.endpoints.resources;

import com.futurealgos.micro.users.dto.payload.docs.ChatPayload;
import com.futurealgos.micro.users.dto.payload.docs.DocPayload;
import com.futurealgos.micro.users.dto.payload.StatusPayload;
import com.futurealgos.micro.users.dto.payload.StatusUpdate;
import com.futurealgos.micro.users.dto.payload.users.NewUser;
import com.futurealgos.micro.users.dto.payload.users.UserRole;
import com.futurealgos.micro.users.dto.response.UserInfo;
import com.futurealgos.micro.users.dto.response.WeightedResponse;
import com.futurealgos.micro.users.exceptions.exceptions.InvalidIdentifierFormat;
import com.futurealgos.micro.users.exceptions.exceptions.NotFoundException;
import com.futurealgos.micro.users.service.RegistrationService;
import com.futurealgos.micro.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.InputMismatchException;

@RestController
@RequestMapping("/")
public class UsersResource {

    @Autowired
    UserService userService;

    @Autowired
    RegistrationService registrationService;

//    Resource APIs

    @PostMapping("create")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String createNewUser(@RequestBody @Valid NewUser payload,
                                BindingResult result) {
        if (result.hasErrors()) {
            throw new InputMismatchException("Invalid Data Format");
        }
        userService.createUser(payload);

        return "Successfully Created User";
    }

    @PutMapping(value = "edit/status")
    public ResponseEntity<WeightedResponse> updateUserStatus(@RequestBody @Valid StatusUpdate payload,
                                   BindingResult result) throws InvalidIdentifierFormat {
        if (result.hasErrors()) throw new InputMismatchException("Invalid Data Format");


        return ResponseEntity.accepted()
                .body(new WeightedResponse("Successfully updated User Status",
                        userService.updateUserStatus(payload, "")));
    }

    @PutMapping(value = "edit/roles")
    public String updateUserRole(@RequestBody @Valid UserRole payload,
                                 BindingResult result) {
        if (result.hasErrors())
            throw new InputMismatchException("Invalid Data Format");
        userService.addUserRole(payload);
        return "Role Successfully Added";
    }


    @GetMapping(value = "info", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserInfo fetchUserData(@RequestParam("id") String id,
                                  @RequestParam(value = "complete", required = false) Boolean complete) throws InvalidIdentifierFormat {
        return userService.getUserInfo(id, complete != null && complete);
    }

    /**
     *
     * @param payload DocPayload Contains Users id , Document name to be requested and Message from Admin to user (not required).
     * @throws NotFoundException if the user ID Does not exist.
     * @return Success Message With the ACCEPTED Status.
     */
    @PostMapping(value = "docs/request", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> requestDocument(@RequestBody DocPayload payload, BindingResult result) throws NotFoundException, InvalidIdentifierFormat {
        if (result.hasErrors())
            throw new InputMismatchException("Invalid Data Format");

        userService.requestDocument(payload);
        return ResponseEntity.accepted().body("Request Has Been Sent Initiated");
    }

    @PutMapping(value = "docs/edit/status")
    public ResponseEntity<WeightedResponse> modifyStatus(@RequestBody StatusPayload payload, BindingResult result){
        return ResponseEntity.accepted().body(new WeightedResponse("Successfully modified Request Status", userService.modifyDocRequestStatus(payload)));
    }

    @PostMapping(value = "docs/edit/chat")
    public WeightedResponse sendMessage(@RequestBody ChatPayload payload, BindingResult result){
        return new WeightedResponse("Message Sent", userService.updateChat(payload));
    }
}
