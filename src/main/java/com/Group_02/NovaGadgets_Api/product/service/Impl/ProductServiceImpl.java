package com.Group_02.NovaGadgets_Api.product.service.Impl;

import com.Group_02.NovaGadgets_Api.category.model.CategoryEntity;
import com.Group_02.NovaGadgets_Api.category.repository.CategoryRepository;
import com.Group_02.NovaGadgets_Api.product.dto.ProductDTO;
import com.Group_02.NovaGadgets_Api.product.model.ProductEntity;
import com.Group_02.NovaGadgets_Api.product.repository.ProductRepository;
import com.Group_02.NovaGadgets_Api.product.service.ProductService;
import com.Group_02.NovaGadgets_Api.productStore.model.ProductStoreEntity;
import com.Group_02.NovaGadgets_Api.productStore.repository.ProductStoreRepository;
import com.Group_02.NovaGadgets_Api.productStore.service.ProductStoreService;
import com.Group_02.NovaGadgets_Api.shared.exception.ResourceNotFoundException;
import com.Group_02.NovaGadgets_Api.shared.exception.ValidationException;
import com.Group_02.NovaGadgets_Api.store.model.StoreEntity;
import com.Group_02.NovaGadgets_Api.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductStoreService productStoreService;

    @Autowired
    ProductStoreRepository productStoreRepository;

    @Override
    public ProductEntity addProduct(ProductDTO productDTO) {
        if(productRepository.existsByName(productDTO.getName())){
            throw new ValidationException("Name already exists");
        }

        CategoryEntity category = categoryRepository.findByName(productDTO.getCategoryName());
        if(category == null){
            throw new ResourceNotFoundException("Categoria no encontrada");
        }

        StoreEntity store = storeRepository.findByName(productDTO.getStoreName());
        if(store == null){
            throw new ResourceNotFoundException("Store no encontrado");
        }

        ProductEntity product = new ProductEntity(0,productDTO.getName(), productDTO.getImage(), productDTO.getDetails(), category);
        product = productRepository.save(product);

        productStoreService.addProductStore(product,store,productDTO.getQuantity(), productDTO.getPrice());

        return product;

    }

    @Override
    public void deleteProduct(Integer id) {
        List<ProductStoreEntity> list = productStoreRepository.findProductStoreByProductId(id);
        for(ProductStoreEntity productStoreEntity : list){
            productStoreService.deleteProductStore(productStoreEntity.getId());
        }
        ProductEntity product = GetById(id);
        productRepository.delete(product);
    }

    @Override
    public List<ProductEntity> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity GetById(Integer id) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        if(product == null){
            throw new ResourceNotFoundException("Product no encontrado");
        }
        return product;
    }
}
