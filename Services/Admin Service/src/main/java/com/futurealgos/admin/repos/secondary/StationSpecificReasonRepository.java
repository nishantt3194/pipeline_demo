package com.futurealgos.admin.repos.secondary;


import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.secondary.reason.StationSpecificReason;
import com.futurealgos.admin.utils.enums.BreakdownType;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StationSpecificReasonRepository extends BaseRepository<StationSpecificReason, UUID>, ReaderRepository<StationSpecificReason> {
    boolean existsByMachine_NameIgnoreCaseAndReason_ReasonIgnoreCase(String name, String reason);

    boolean existsByMachine_NameAndReason_ReasonIgnoreCaseAndStation_NameIgnoreCase(String name, String reason, String station);

    List<StationSpecificReason> findReasonAssociationsByMachineAndReason_TypeAndReason_Status(Machine machine, BreakdownType type, boolean status);

}
