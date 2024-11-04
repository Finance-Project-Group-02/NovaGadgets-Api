package com.Group_02.NovaGadgets_Api.productStore.service.Impl;

import com.Group_02.NovaGadgets_Api.product.model.ProductEntity;
import com.Group_02.NovaGadgets_Api.productStore.model.ProductStoreEntity;
import com.Group_02.NovaGadgets_Api.productStore.repository.ProductStoreRepository;
import com.Group_02.NovaGadgets_Api.productStore.service.ProductStoreService;
import com.Group_02.NovaGadgets_Api.shared.exception.ResourceNotFoundException;
import com.Group_02.NovaGadgets_Api.shared.exception.ValidationException;
import com.Group_02.NovaGadgets_Api.store.model.StoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStoreServiceImpl implements ProductStoreService {

    @Autowired
    ProductStoreRepository productStoreRepository;

    @Override
    public void addProductStore(ProductEntity product, StoreEntity store, Integer quantity, Double price) {
        ProductStoreEntity productStoreEntity = new ProductStoreEntity();
        productStoreEntity.setProduct(product);
        productStoreEntity.setStore(store);
        productStoreEntity.setQuantity(quantity);
        productStoreEntity.setPrice(price);

        productStoreRepository.save(productStoreEntity);
    }

    @Override
    public void deleteProductStore(Integer id) {
        ProductStoreEntity productStore = GetById(id);
        productStoreRepository.delete(productStore);
    }

    @Override
    public List<ProductStoreEntity> getAllProductStore() {
        return productStoreRepository.findAll();
    }

    @Override
    public ProductStoreEntity GetById(Integer id) {
        ProductStoreEntity productStore = productStoreRepository.findById(id).orElse(null);
        if(productStore == null){
            throw new ResourceNotFoundException("ProductStore no encontrado");
        }
        return productStore;
    }
}
