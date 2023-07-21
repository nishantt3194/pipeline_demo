package com.futurealgos.admin.services.machines;

import com.futurealgos.admin.dto.response.machine.StationInfo;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.admin.repos.secondary.StationRepository;
import com.futurealgos.data.service.ReaderTemplate;
import com.futurealgos.specs.objects.DefaultSearchResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class StationService extends ReaderTemplate<Station, UUID, StationInfo, Void, DefaultSearchResponse> {
    public StationService(StationRepository reader) {
        super(Station.class, reader);
    }
}
