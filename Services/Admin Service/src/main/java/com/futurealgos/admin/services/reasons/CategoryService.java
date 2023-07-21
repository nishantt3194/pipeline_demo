package com.futurealgos.admin.services.reasons;

import com.futurealgos.admin.dto.response.downtime.CategoryInfo;
import com.futurealgos.admin.models.primary.Category;
import com.futurealgos.admin.repos.primary.CategoryRepository;
import com.futurealgos.data.service.ServiceTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService extends ServiceTemplate<Category, UUID, CategoryInfo, Void, CategoryInfo, Void, Void> {

    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        super(Category.class, categoryRepository, categoryRepository);

    }

}