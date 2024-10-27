package com.Group_02.NovaGadgets_Api.product.service;

import com.Group_02.NovaGadgets_Api.product.dto.ProductDTO;
import com.Group_02.NovaGadgets_Api.product.model.ProductEntity;
import com.Group_02.NovaGadgets_Api.productStore.model.ProductStoreEntity;

import java.util.List;

public interface ProductService {
    public abstract ProductEntity addProduct(ProductDTO productDTO);
    public abstract void deleteProduct(Integer id);
    public abstract List<ProductEntity> getAllProduct();
    public abstract ProductEntity GetById(Integer id);
}
