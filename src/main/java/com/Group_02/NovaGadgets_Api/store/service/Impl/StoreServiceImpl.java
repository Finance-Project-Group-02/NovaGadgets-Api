package com.Group_02.NovaGadgets_Api.store.service.Impl;

import com.Group_02.NovaGadgets_Api.productStore.model.ProductStoreEntity;
import com.Group_02.NovaGadgets_Api.productStore.repository.ProductStoreRepository;
import com.Group_02.NovaGadgets_Api.productStore.service.ProductStoreService;
import com.Group_02.NovaGadgets_Api.shared.exception.ResourceNotFoundException;
import com.Group_02.NovaGadgets_Api.shared.exception.ValidationException;
import com.Group_02.NovaGadgets_Api.store.model.StoreEntity;
import com.Group_02.NovaGadgets_Api.store.repository.StoreRepository;
import com.Group_02.NovaGadgets_Api.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ProductStoreService productStoreService;

    @Autowired
    ProductStoreRepository productStoreRepository;

    @Override
    public StoreEntity addStore(StoreEntity store) {
        if(storeRepository.existsByName(store.getRuc())){
            throw new ValidationException("Name already exists");
        }

        if(storeRepository.existsByRuc(store.getRuc())){
            throw new ValidationException("RUC already exists");
        }

        StoreEntity storeCreated = new StoreEntity(0,store.getName(),store.getRuc());
        return storeRepository.save(storeCreated);
    }

    @Override
    public void deleteStore(Integer id) {
        List<ProductStoreEntity> list = productStoreRepository.findProductStoreByStoreId(id);
        for(ProductStoreEntity productStoreEntity : list){
            productStoreService.deleteProductStore(productStoreEntity.getId());
        }
        StoreEntity store = getById(id);
        storeRepository.delete(store);
    }

    @Override
    public List<StoreEntity> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public StoreEntity getById(Integer id) {
        StoreEntity store = storeRepository.findById(id).orElse(null);
        if(store == null){
            throw new ResourceNotFoundException("Store no encontrado");
        }
        return store;
    }
}
