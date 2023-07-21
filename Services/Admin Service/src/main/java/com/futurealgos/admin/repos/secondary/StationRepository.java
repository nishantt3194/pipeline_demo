package com.futurealgos.admin.repos.secondary;


import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.data.repos.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StationRepository extends BaseRepository<Station, UUID> {

    @Query("select s.name from station s where s.machine= :machine and s.status=true")
    List<String> getStations(Machine machine);
}
