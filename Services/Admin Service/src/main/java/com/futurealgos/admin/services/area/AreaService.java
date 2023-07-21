package com.futurealgos.admin.services.area;

import com.futurealgos.admin.dto.payload.area.NewAreaPayload;
import com.futurealgos.admin.dto.response.area.AreaInfo;
import com.futurealgos.admin.dto.response.area.AreaSearch;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.views.UserAreaAssociation;
import com.futurealgos.admin.repos.primary.AreaRepository;
import com.futurealgos.admin.repos.views.UserAreaAssociationRepository;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.data.service.ServiceTemplate;
import com.futurealgos.specs.utils.PayloadConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

@Service
public class AreaService extends ServiceTemplate<Area, UUID, AreaInfo, AreaInfo, AreaSearch, NewAreaPayload, NewAreaPayload> {

    AreaRepository areaRepository;
    UserAreaAssociationRepository userAreaAssociationRepository;

    public AreaService(AreaRepository repository, UserAreaAssociationRepository userAreaAssociationRepository) {
        super(Area.class, repository, repository);
        Assert.notNull(repository, "AreaRepository must not be null!");
        Assert.notNull(userAreaAssociationRepository, "UserAreaAssociationRepository must not be null!");
        this.areaRepository = repository;
        this.userAreaAssociationRepository = userAreaAssociationRepository;
        this.converter = NewAreaPayload::convert;
    }

    public List<UserAreaAssociation> associations(Area area) {
        return userAreaAssociationRepository.findAllByArea(area);
    }

}
