package com.futurealgos.admin.repos.primary;

import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.Shift;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShiftRepository extends BaseRepository<Shift, UUID>, ReaderRepository<Shift> {

    Optional<Shift> findShiftByNameAndArea(String name, Area area);

    Optional<Shift> findFirstByNameAndArea_NameIsIgnoreCase(String name, String area);


    @Query("select distinct d.name from shift as d")
    List<String> getShiftNames();

    List<Shift> findByArea(Area area);

    List<Shift> findByAreaOrderByStartTimeAsc(Area area);

    Shift findFirstByArea(Area area);

    @Query("select s from shift as s where s.area.id=:area and s.id not in (select e.shift from entry e where e.machine.id=:machine and e.shiftDate=:date)")
    List<Shift> findPendingShift(UUID machine, LocalDate date, UUID area);

}
