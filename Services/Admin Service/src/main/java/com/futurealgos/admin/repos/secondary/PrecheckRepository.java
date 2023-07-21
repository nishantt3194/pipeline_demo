package com.futurealgos.admin.repos.secondary;

import com.futurealgos.admin.models.secondary.machine.Precheck;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PrecheckRepository extends BaseRepository<Precheck, UUID>, ReaderRepository<Precheck> {


}
