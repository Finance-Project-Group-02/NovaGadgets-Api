package com.Group_02.NovaGadgets_Api.category.service;

import com.Group_02.NovaGadgets_Api.category.model.CategoryEntity;

import java.util.List;

public interface CategoryService {
    public abstract List<CategoryEntity> findAll();
    public abstract CategoryEntity getById(Integer id);
}
