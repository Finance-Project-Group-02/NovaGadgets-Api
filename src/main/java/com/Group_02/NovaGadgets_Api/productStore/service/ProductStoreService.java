package com.Group_02.NovaGadgets_Api.productStore.service;

import com.Group_02.NovaGadgets_Api.product.model.ProductEntity;
import com.Group_02.NovaGadgets_Api.productStore.model.ProductStoreEntity;
import com.Group_02.NovaGadgets_Api.store.model.StoreEntity;

import java.util.List;

public interface ProductStoreService {
    public abstract void addProductStore(ProductEntity product, StoreEntity store, Integer quantity, Double price);
    public abstract void deleteProductStore(Integer id);
    public abstract List<ProductStoreEntity> getAllProductStore();
    public abstract ProductStoreEntity GetById(Integer id);
}
