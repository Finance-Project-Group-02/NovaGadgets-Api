package com.Group_02.NovaGadgets_Api.store.service;

import com.Group_02.NovaGadgets_Api.store.model.StoreEntity;

import java.util.List;

public interface StoreService {
    public abstract StoreEntity addStore(StoreEntity store);
    public abstract void deleteStore(Integer id);
    public abstract List<StoreEntity> getAll();
    public abstract StoreEntity getById(Integer id);
}
