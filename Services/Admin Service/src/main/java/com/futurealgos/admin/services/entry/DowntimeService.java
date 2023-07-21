package com.futurealgos.admin.services.entry;

import com.futurealgos.admin.dto.response.downtime.DowntimeMinimal;
import com.futurealgos.admin.dto.response.downtime.DowntimeSearch;
import com.futurealgos.admin.models.primary.Downtime;
import com.futurealgos.admin.repos.secondary.DowntimeRepository;
import com.futurealgos.data.service.ServiceTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DowntimeService extends ServiceTemplate<Downtime, UUID, Void, DowntimeMinimal, DowntimeSearch, Void, Void> {

    DowntimeRepository downtimeRepository;

    public DowntimeService(DowntimeRepository repository) {
        super(Downtime.class, repository, repository);
        this.downtimeRepository = repository;
    }

}
