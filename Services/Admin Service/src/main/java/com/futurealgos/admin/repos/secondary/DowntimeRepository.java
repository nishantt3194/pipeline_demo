package com.futurealgos.admin.repos.secondary;


import com.futurealgos.admin.dto.response.machine.OeeParetoQuery;
import com.futurealgos.admin.dto.response.machine.ParetoQuery;
import com.futurealgos.admin.dto.response.shifts.UnregisteredBreakdowns;
import com.futurealgos.admin.models.primary.Downtime;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DowntimeRepository extends BaseRepository<Downtime, UUID>, ReaderRepository<Downtime> {
    @Query("select sum(b.time) as downtime,b.reason as reason from downtime as b where b.entry.machine =:machine and b.entry.shiftDate between :start and :end group by reason order by downtime desc")
    List<ParetoQuery> getParetoData(Machine machine, LocalDate start, LocalDate end);

    @Query("select b.reason as reason, b.type as type, b.association as association, count(b) as occurrence from downtime b where b.reason not in (select distinct r.reason from reason r) group by reason, association")
    List<UnregisteredBreakdowns> getUnregisteredBreakdowns();

    @Query("select sum(b.time) as downtime,b.runtimeCategory as category from downtime as b where b.entry.machine =:machine and b.entry.shiftDate between :start and :end group by category order by downtime desc")
    List<OeeParetoQuery> getOeeParetoData(Machine machine, LocalDate start, LocalDate end );


    List<Downtime> findAllByAssociation(UUID association);

}
