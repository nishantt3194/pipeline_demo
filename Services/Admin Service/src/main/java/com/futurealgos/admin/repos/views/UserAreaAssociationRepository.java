package com.futurealgos.admin.repos.views;

import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.models.views.UserAreaAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserAreaAssociationRepository extends JpaRepository<UserAreaAssociation, UUID>, JpaSpecificationExecutor<UserAreaAssociation> {

    List<UserAreaAssociation> findAllByUser(User user);


    List<UserAreaAssociation> findAllByArea(Area area);

}
