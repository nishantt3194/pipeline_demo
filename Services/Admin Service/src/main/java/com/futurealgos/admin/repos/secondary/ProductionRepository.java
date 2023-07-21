package com.futurealgos.admin.repos.secondary;

import com.futurealgos.admin.models.secondary.entry.Production;
import com.futurealgos.data.repos.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductionRepository extends BaseRepository<Production, UUID> {

}
