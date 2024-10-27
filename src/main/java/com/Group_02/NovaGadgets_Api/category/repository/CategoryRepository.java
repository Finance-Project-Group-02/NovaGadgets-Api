package com.Group_02.NovaGadgets_Api.category.repository;

import com.Group_02.NovaGadgets_Api.category.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    CategoryEntity findByName(String name);
}
