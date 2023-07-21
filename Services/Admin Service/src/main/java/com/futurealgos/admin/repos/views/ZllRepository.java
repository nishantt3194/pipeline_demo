package com.futurealgos.admin.repos.views;

import com.futurealgos.admin.models.views.ZllReport;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ZllRepository extends BaseRepository<ZllReport, UUID>, ReaderRepository<ZllReport> {

}
