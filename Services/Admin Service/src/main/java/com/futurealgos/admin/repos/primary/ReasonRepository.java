package com.futurealgos.admin.repos.primary;

import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.Reason;
import com.futurealgos.admin.utils.enums.BreakdownType;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReasonRepository extends BaseRepository<Reason, UUID>, ReaderRepository<Reason> {

    Reason findByReasonIgnoreCase(String reason);

    boolean existsByReason(String reason);

    boolean existsByReasonAndType(String reason, BreakdownType type);

    Optional<Reason> findReasonByReason(String reason);

    @Query("select distinct r.reason from reason as r")
    List<String> getAllReasons();

    @Query("select distinct r.reason from reason  as r where r.type= :type and r.status= :status")
    List<String> getReasonsFromTypeAndStatus(BreakdownType type, boolean status);

    List<Reason> findAllByType(BreakdownType type);

    @Query("select r from reason as r group by r.reason")
    List<Reason> findAllDistinct();

    @Query("select r from reason r inner join r.associations associations where associations.machine = ?1")
    List<Reason> findByAssociations_Machine(Machine machine);


}
