package com.Group_02.NovaGadgets_Api.product.repository;

import com.Group_02.NovaGadgets_Api.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    Boolean existsByName(String name);
}
