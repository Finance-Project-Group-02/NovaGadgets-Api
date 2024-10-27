package com.Group_02.NovaGadgets_Api.category.service.Impl;

import com.Group_02.NovaGadgets_Api.category.model.CategoryEntity;
import com.Group_02.NovaGadgets_Api.category.repository.CategoryRepository;
import com.Group_02.NovaGadgets_Api.category.service.CategoryService;
import com.Group_02.NovaGadgets_Api.shared.exception.ResourceNotFoundException;
import com.Group_02.NovaGadgets_Api.store.model.StoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity getById(Integer id) {
        CategoryEntity category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            throw new ResourceNotFoundException("Categoria no encontrada");
        }
        return category;
    }
}
