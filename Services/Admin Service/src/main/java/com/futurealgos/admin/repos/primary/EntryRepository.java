package com.futurealgos.admin.repos.primary;

import com.futurealgos.admin.dto.response.entry.EntryMinimal;
import com.futurealgos.admin.dto.response.machine.BOSQuery;
import com.futurealgos.admin.dto.response.machine.OeeBOSQuery;
import com.futurealgos.admin.models.primary.*;
import com.futurealgos.admin.utils.enums.EntryStatus;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EntryRepository extends BaseRepository<Entry, UUID>, ReaderRepository<Entry> {
    List<Entry> findByMachineAndShiftDateBetween(Machine machine, LocalDate shiftDateStart, LocalDate shiftDateEnd);
    List<Entry> findByShiftDate(LocalDate shiftDate);
    Entry findFirstByShift_AreaAndShiftDateOrderByOeeDesc(Area area, LocalDate shiftDate);
    List<Entry> findByOperator(User operator);
    boolean existsByShiftAndShiftDateAndMachine(Shift shift, LocalDate shiftDate, Machine machine);

    long countByMachineAndShiftDateBetween(Machine machine, LocalDate dateStart, LocalDate dateEnd);

    @Query("select sum(e.totalProduction) as qty,max(e.shiftDate) as timestamp,min(e.shiftDate) as tstart,max(e.shiftDate) as tend, count(e) as shifts, week(e.shiftDate) AS grouper from entry as e where e.machine=:machine and e.shiftDate between :start and :end group by grouper order by timestamp")
    List<BOSQuery> getBOSData(Machine machine, LocalDate start, LocalDate end);

    @Query("select distinct s.shift from entry as s where s.machine=:machine and s.shiftDate=:date")
    List<Shift> getShiftsCompleted(Machine machine, LocalDate date);

    @Query("select avg(e.oee) as oee , max(e.shiftDate) as timestamp, min(e.shiftDate) as tstart, max(e.shiftDate) as tend, count(e) as shifts,e.oeeTarget as oeeTarget,e.lsaTarget as lsaTarget, week(e.shiftDate) AS grouper from entry as e where e.machine=:machine and e.shiftDate between :start and :end group by grouper order by timestamp")
    List<OeeBOSQuery> getOEEBOSData(Machine machine , LocalDate start , LocalDate end);

   Optional<Entry> findFirstByOperatorOrderByShiftDateDesc(User operator);
   List<Entry> findTop5ByOperatorOrderByShiftDateDesc(User operator);
   List<Entry> findTop3ByOrderByCreatedOnDesc();

   Entry findFirstByShift_AreaOrderByShiftDateDesc(Area area);

}
