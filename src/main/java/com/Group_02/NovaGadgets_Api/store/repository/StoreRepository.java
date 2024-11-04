package com.Group_02.NovaGadgets_Api.store.repository;

import com.Group_02.NovaGadgets_Api.store.model.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity,Integer> {
    Boolean existsByRuc(String ruc);
    Boolean existsByName(String name);
    StoreEntity findByName(String name);
}
