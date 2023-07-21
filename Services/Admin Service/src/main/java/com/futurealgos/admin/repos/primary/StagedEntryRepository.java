package com.futurealgos.admin.repos.primary;

import com.futurealgos.admin.models.secondary.entry.StagedEntry;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StagedEntryRepository extends BaseRepository<StagedEntry, UUID> , ReaderRepository<StagedEntry> {
}
