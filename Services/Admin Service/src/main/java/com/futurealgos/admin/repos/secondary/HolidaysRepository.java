package com.futurealgos.admin.repos.secondary;

import com.futurealgos.admin.models.primary.Holiday;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface HolidaysRepository extends BaseRepository<Holiday, UUID>, ReaderRepository<Holiday> {

    @Query("select count(h) from holidays h where h.date between ?1 and ?2")
    long countByDateBetween(LocalDate dateStart, LocalDate dateEnd);

}
