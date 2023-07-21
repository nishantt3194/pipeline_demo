package com.futurealgos.admin.repos.primary;

import com.futurealgos.admin.models.primary.Category;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends BaseRepository<Category, UUID>, ReaderRepository<Category> {

    Category findCategoryByNameIgnoreCase(String name);
}
