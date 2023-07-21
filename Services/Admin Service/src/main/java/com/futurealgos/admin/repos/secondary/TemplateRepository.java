package com.futurealgos.admin.repos.secondary;


import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.secondary.entry.Template;
import com.futurealgos.data.repos.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends BaseRepository<Template, UUID> {

    List<Template> findByAreaAndTypeIn(Area area, List<Template.Type> type);

    List<Template> findByAreaAndType(Area area, Template.Type type);

    List<Template> findByArea(Area area);

    List<Template> findByArea_NameIsNotLike(String area);

    Optional<Template> findByDescriptionIgnoreCaseAndType(String description, Template.Type type);

    @Query("select t from template t where t.description = ?1")
    Optional<Template> findByDescription(String description);



}
