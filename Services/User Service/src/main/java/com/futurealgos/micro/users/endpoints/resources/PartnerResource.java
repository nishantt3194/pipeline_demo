/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.endpoints.resources;

import com.futurealgos.micro.users.dto.payload.StatusUpdate;
import com.futurealgos.micro.users.dto.payload.users.NewAssessor;
import com.futurealgos.micro.users.dto.payload.users.NewPartner;
import com.futurealgos.micro.users.dto.payload.users.PreRegisterDto;
import com.futurealgos.micro.users.dto.response.*;
import com.futurealgos.micro.users.exceptions.exceptions.InvalidIdentifierFormat;
import com.futurealgos.micro.users.models.base.Partner;
import com.futurealgos.micro.users.service.PartnerService;
import com.futurealgos.micro.users.service.RegistrationService;
import com.futurealgos.micro.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Package: com.futurealgos.micro.users.endpoints.resources
 * Project: Prasad Psycho
 * Created On: 18/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/

@RestController
@RequestMapping("/partners/")
public class PartnerResource {
    @Autowired
    UserService userService;

    @Autowired
    PartnerService partnerService;

    @Autowired
    RegistrationService registrationService;

    /**
     * Builds and returns Profile data for the respective Partner.
     * <p>
     * Response Constructed Consists,<br/>
     * Basic Details of the Partner<br/>
     * Activity Logs<br/>
     * Document Requests<br/>
     * Transaction History<br/>
     * </p>
     *
     * <b>TODO: Remove id dependency, instead get id from security partner</b>
     *
     * @param id ID of the Partner
     * @return Profile data for the respective user.
     */
    @GetMapping(value = "profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public PartnerInfo fetchUserData(@RequestParam("id") String id) {
        return partnerService.info(id);
    }

    @GetMapping(value = "info", produces = MediaType.APPLICATION_JSON_VALUE)
    public PartnerInfo fetchPartnerProfile(@RequestParam("id") String id) {
        return partnerService.info(id);
    }

    @PutMapping(value = "edit/status")
    public ResponseEntity<WeightedResponse> updatePartnerStatus(@RequestBody @Valid StatusUpdate payload,
                                                             BindingResult result) throws InvalidIdentifierFormat {
        if (result.hasErrors()) throw new InputMismatchException("Invalid Data Format");

        return ResponseEntity.accepted()
                .body(new WeightedResponse("Successfully updated User Status",
                        partnerService.updateStatus(payload, "")));
    }

    @PutMapping(value = "edit/status/approve")
    public ResponseEntity<WeightedResponse> updateUserStatus(@RequestBody String id) throws InvalidIdentifierFormat {
        return ResponseEntity.accepted()
                .body(new WeightedResponse("Successfully Approved Partner", partnerService.approve(id, "")));
    }

    /**
     * Registration API for Partners. Validates the data and registers the partner.
     * <p>
     * Initial Status of Partner is set as Pending. Once partner submits any required documents,
     * or on Administrator's discretion as per the policy, the status is set to Active.
     * <p>
     * Once Active Partner has access to all the features of the system.
     * </p>
     *
     * @param payload Payload containing the data of the Partner to be registered.
     * @param result  Contains validation errors if any.
     * @return Message indicating successful registration.
     * Any error will be thrown as exception by service called internally
     */
    @PostMapping("register")
    public ResponseEntity<String> registerPartner(@RequestBody @Valid NewPartner payload, BindingResult result) {
        if (result.hasErrors()) {
            throw new InputMismatchException("Invalid Data Format");
        }
        partnerService.register(payload);

        return ResponseEntity.accepted().body("Registration Successful");
    }

    /**
     * Validates the data and generates a link to be used for registration
     * <p>
     * API for generating a Registration Link via which users can register themselves as partners on the platform
     * </p>
     *
     * @param payload Payload containing the basic data of the Partner to generate Registration Link.
     * @param result  consists validation errors if any.
     * @return Message indicating successful generation of registration link. Any error will be thrown
     * as exception by service called internally
     */
    @PostMapping(value = "register/init")
    public ResponseEntity<String> initiatePreRegistration(@RequestBody @Valid PreRegisterDto payload,
                                                          BindingResult result) {
        if (result.hasErrors())
            throw new InputMismatchException("Invalid Data Format");

        registrationService.init(payload);

        return ResponseEntity.accepted().body("Registration Link has been sent successfully");
    }

    @GetMapping(value = "register/init/retrieve")
    public RegisterInfo registrationInfo(@RequestParam("id") String id) {
        return registrationService.info(id);
    }


    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<MinimalPartner>> listPartners(
            @RequestParam("offset") int offset,
            @RequestParam("size") int size,
            @RequestParam(value = "status", required = false) Partner.Status status,
            @RequestParam(value = "filter", required = false) String filter) {
        return ResponseEntity.ok(partnerService.list(Pageable.unpaged(), status));
    }

    @GetMapping("assessors")
    public ResponseEntity<List<MinimalUser>> accessors(@RequestParam("partner") String partner) {
        return ResponseEntity.ok(partnerService.fetchAssessors(partner));
    }

    @PostMapping("assessors/new")
    public ResponseEntity<String> createAccessor(@RequestBody NewAssessor payload) {
        partnerService.addAssessor(payload, "");
        return ResponseEntity.accepted().body("Accessor Added Successfully");
    }

    @GetMapping("administrators")
    public ResponseEntity<List<MinimalUser>> administrators(@RequestParam("partner") String partner) {
        return ResponseEntity.ok(partnerService.fetchAdministrators(partner));
    }

    @GetMapping(value = "list/unregistered")
    public ResponseEntity<Page<MinRegister>> preRegisteredList(@RequestParam("offset") int offset, @RequestParam("size") int size) {
        return ResponseEntity.ok(registrationService.list(Pageable.unpaged()));
    }

    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PartnerSearch>> searchAll() {
        return ResponseEntity.ok(userService.search());
    }
}
