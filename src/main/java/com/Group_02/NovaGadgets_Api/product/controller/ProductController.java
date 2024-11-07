package com.Group_02.NovaGadgets_Api.product.controller;

import com.Group_02.NovaGadgets_Api.product.dto.ProductDTO;
import com.Group_02.NovaGadgets_Api.product.model.ProductEntity;
import com.Group_02.NovaGadgets_Api.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductEntity> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<ProductEntity>(productService.addProduct(productDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/products/id/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id")Integer id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfull", HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductEntity>> getAllProducts(){
        return new ResponseEntity<List<ProductEntity>>(productService.getAllProduct(), HttpStatus.OK);
    }

    @GetMapping("/products/id/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable("id")Integer id){
        return new ResponseEntity<ProductEntity>(productService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/products/response")
    public ResponseEntity<List<ProductDTO>> getAllProductsResponse(){
        return new ResponseEntity<List<ProductDTO>>(productService.getAllProductsResponse(), HttpStatus.OK);
    }
}
