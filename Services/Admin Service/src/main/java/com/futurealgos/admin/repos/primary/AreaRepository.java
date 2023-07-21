package com.futurealgos.admin.repos.primary;

import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AreaRepository extends BaseRepository<Area, UUID>, ReaderRepository<Area> {


}
