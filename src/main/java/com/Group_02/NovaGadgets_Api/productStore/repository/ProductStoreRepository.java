package com.Group_02.NovaGadgets_Api.productStore.repository;

import com.Group_02.NovaGadgets_Api.productStore.model.ProductStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductStoreRepository extends JpaRepository<ProductStoreEntity,Integer> {
    @Query(nativeQuery = true, value = "" +
            "SELECT i.* FROM product_store i " +
            "WHERE i.product_id = :productId")
    List<ProductStoreEntity> findProductStoreByProductId(@Param("productId") Integer productId);

    @Query(nativeQuery = true, value = "" +
            "SELECT i.* FROM product_store i " +
            "WHERE i.store_id = :storeId")
    List<ProductStoreEntity> findProductStoreByStoreId(@Param("storeId") Integer storeId);
}
