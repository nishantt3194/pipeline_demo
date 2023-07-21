package com.futurealgos.admin.endpoints;

import com.azure.core.annotation.ReturnValueWireType;
import com.futurealgos.admin.dto.payload.entry.EntryPayload;
import com.futurealgos.admin.dto.response.WeightedResponse;
import com.futurealgos.admin.dto.response.entry.EntryInfo;
import com.futurealgos.admin.dto.response.entry.EntryMinimal;
import com.futurealgos.admin.dto.response.entry.ZllInfo;
import com.futurealgos.admin.dto.response.shifts.TemplateMinimal;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.models.secondary.entry.StagedEntry;
import com.futurealgos.admin.models.secondary.entry.Template;
import com.futurealgos.admin.models.views.UserAreaAssociation;
import com.futurealgos.admin.services.administration.UserService;
import com.futurealgos.admin.services.area.TemplateService;
import com.futurealgos.admin.services.entry.EntryProcessingService;
import com.futurealgos.admin.services.entry.EntryService;
import com.futurealgos.admin.services.entry.StagedEntryService;
import com.futurealgos.admin.services.entry.ZllService;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.utils.enums.EntryStatus;
import com.futurealgos.admin.utils.enums.Role;
import com.futurealgos.admin.utils.enums.SaveType;
import com.futurealgos.specs.utils.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/entry/")
public class EntryResource {

    final UserService userService;
    final
    ZllService zllService;
    final
    EntryService entryService;
    final
    EntryProcessingService entryProcessingService;
    final
    StagedEntryService stagedEntryService;
    final
    TemplateService templateService;

    @Autowired
    MachineService machineService;

    public EntryResource(
            UserService userService,
            ZllService zllService,
            EntryService entryService,
            EntryProcessingService entryProcessingService,
            StagedEntryService stagedEntryService,
            TemplateService templateService) {
        this.userService = userService;
        this.zllService = zllService;
        this.entryService = entryService;
        this.entryProcessingService = entryProcessingService;
        this.stagedEntryService = stagedEntryService;
        this.templateService = templateService;
    }

    @PostMapping(value = "process/{saveType}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeightedResponse> entry(@RequestBody EntryPayload payload,
                                                  @PathVariable("saveType") SaveType type,
                                                  @AuthenticationPrincipal User user) {
        StagedEntry entry;
        if (type.equals(SaveType.EDIT)) {
            return ResponseEntity.accepted()
                    .body(new WeightedResponse("Entry Edited successfully",
                            entryProcessingService.update(payload.id(), payload, user.getId().toString()) ));
        }

        if (payload.id() != null) {
            entry = stagedEntryService.update(payload.id(), payload, user.getId().toString(), new Date());
        } else {
            entry = stagedEntryService.create(payload, user.getId().toString(), new Date());
        }

        if (type.equals(SaveType.SUBMIT)) {
            return ResponseEntity.accepted()
                    .body(new WeightedResponse("Entry Submitted successfully",
                            entryProcessingService.submit(entry, user.getId().toString())));
        }

        else if (type.equals(SaveType.SAVE))
            return ResponseEntity.accepted().body(new WeightedResponse( " Entry Saved Successfully",null));
        else
            return ResponseEntity.accepted().body(new WeightedResponse( " ERROR , Entry Not Saved",null));
    }

    @GetMapping("staged")
    public EntryPayload stagedEntry(@RequestParam("id") String id,
                                    @RequestParam(value = "edited", required = false, defaultValue = "false") Boolean edited) {
        if (edited) {
            return entryService.fetchEditPayload(id);
        }
        return stagedEntryService.info(id);
    }

    @GetMapping("list")
    public Page<EntryMinimal> list(@RequestParam(required = false, defaultValue = "-1") Integer offset,
                                   @RequestParam(required = false, defaultValue = "-1") Integer size,
                                   @RequestParam(value = "start", required = false) String startRaw,
                                   @RequestParam(value = "end", required = false) String endRaw,
                                   @RequestParam(value = "id", required = false) String machineId,
                                   @RequestParam(value = "status", required = false, defaultValue = "SUBMITTED") EntryStatus status,
                                   @AuthenticationPrincipal User user) {
        List<SearchFilter<?>> filters = new ArrayList<>();
        if (machineId != null) {
            Machine machine = machineService.fetch(machineId);

            filters.add(SearchFilter.is("machine", machine));
        }
        if (startRaw != null && endRaw != null) {
            LocalDate start = LocalDate.parse(startRaw, DateTimeFormatter.ofPattern("dd-MM-yyyy")).minusDays(1);
            LocalDate end = LocalDate.parse(endRaw, DateTimeFormatter.ofPattern("dd-MM-yyyy")).plusDays(1);
            filters.add(SearchFilter.isAfter("shiftDate", start));
            filters.add(SearchFilter.isBefore("shiftDate", end));

        } else if (status.equals(EntryStatus.STAGED)) {
            filters.add(SearchFilter.isAfter("shiftDate", LocalDate.now().minusDays(7)));
        } else if (status.equals(EntryStatus.SUBMITTED)) {
            filters.add(SearchFilter.is("shiftDate", LocalDate.now()));
        }

        return switch (status) {
            case STAGED -> stagedEntryService.list(offset, size, new String[0], filters.toArray(SearchFilter[]::new));
            case SUBMITTED -> entryService.list(offset, size, new String[0], filters.toArray(SearchFilter[]::new));
        };
    }

    @GetMapping(value = "templates/{type}")
    public List<TemplateMinimal> fetchProductionTemplates(@PathVariable("type") String type, @RequestParam("area") String area, @RequestParam("product") String product) {
        List<Template.Type> types;
        if (type.equalsIgnoreCase("production"))
            types = List.of(Template.Type.TOTAL, Template.Type.PRODUCTION);
        else if (type.equalsIgnoreCase("rejection"))
            types = List.of(Template.Type.REJECTION, Template.Type.EXTRA);
        else
            throw new IllegalStateException("Invalid Parameter Template Type Provided");

        return templateService.list(product, area, types);
    }


    @GetMapping("zll")
    public ZllInfo zllReport(
            @RequestParam("area") String areaId,
            @RequestParam("start") String startRaw,
            @RequestParam("end") String endRaw
    ) {
        LocalDate start = LocalDate.parse(startRaw, DateTimeFormatter.ofPattern("dd-MM-yyyy")).minusDays(1);
        LocalDate end = LocalDate.parse(endRaw, DateTimeFormatter.ofPattern("dd-MM-yyyy")).plusDays(1);

        return zllService.report(areaId, start, end);
    }

    @GetMapping("info")
    public EntryInfo info(@RequestParam String id) {
        return entryService.info(id);
    }


}
