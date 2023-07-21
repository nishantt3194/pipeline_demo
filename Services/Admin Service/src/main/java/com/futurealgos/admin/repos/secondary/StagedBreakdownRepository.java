package com.futurealgos.admin.repos.secondary;

import com.futurealgos.admin.models.secondary.entry.StagedBreakdown;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StagedBreakdownRepository extends BaseRepository<StagedBreakdown, UUID>, ReaderRepository<StagedBreakdown> {

}
