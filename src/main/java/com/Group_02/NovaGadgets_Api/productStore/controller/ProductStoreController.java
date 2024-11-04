package com.Group_02.NovaGadgets_Api.productStore.controller;

import com.Group_02.NovaGadgets_Api.product.model.ProductEntity;
import com.Group_02.NovaGadgets_Api.productStore.model.ProductStoreEntity;
import com.Group_02.NovaGadgets_Api.productStore.service.ProductStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductStoreController {
    @Autowired
    ProductStoreService productStoreService;

    @DeleteMapping("/productStore/id/{id}")
    public ResponseEntity<String> deleteProductStore(@PathVariable("id")Integer id){
        productStoreService.deleteProductStore(id);
        return new ResponseEntity<>("ProductStore deleted successfull", HttpStatus.OK);
    }

    @GetMapping("/productStore")
    public ResponseEntity<List<ProductStoreEntity>> getAllProductStore(){
        return new ResponseEntity<List<ProductStoreEntity>>(productStoreService.getAllProductStore(), HttpStatus.OK);
    }

    @GetMapping("/productStore/id/{id}")
    public ResponseEntity<ProductStoreEntity> getProductStoreById(@PathVariable("id")Integer id){
        return new ResponseEntity<ProductStoreEntity>(productStoreService.GetById(id), HttpStatus.OK);
    }
}
