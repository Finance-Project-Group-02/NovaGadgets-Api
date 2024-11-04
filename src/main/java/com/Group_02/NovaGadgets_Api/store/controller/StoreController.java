package com.Group_02.NovaGadgets_Api.store.controller;

import com.Group_02.NovaGadgets_Api.factura.dto.FacturaRequestDTO;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.store.model.StoreEntity;
import com.Group_02.NovaGadgets_Api.store.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StoreController {
    @Autowired
    StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity<StoreEntity> addStore(@Valid @RequestBody StoreEntity store) {
        return new ResponseEntity<StoreEntity>(storeService.addStore(store), HttpStatus.CREATED);
    }

    @DeleteMapping("/stores/id/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable("id")Integer id){
        storeService.deleteStore(id);
        return new ResponseEntity<>("Store deleted successfull", HttpStatus.OK);
    }

    @GetMapping("/stores")
    public ResponseEntity<List<StoreEntity>> getAllStore(){
        return new ResponseEntity<List<StoreEntity>>(storeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/stores/id/{id}")
    public ResponseEntity<StoreEntity> getById(@PathVariable("id")Integer id){
        return new ResponseEntity<StoreEntity>(storeService.getById(id), HttpStatus.OK);
    }
}
