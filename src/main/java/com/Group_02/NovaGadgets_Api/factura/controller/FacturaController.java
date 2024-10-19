package com.Group_02.NovaGadgets_Api.factura.controller;

import com.Group_02.NovaGadgets_Api.factura.dto.FacturaRequestDTO;
import com.Group_02.NovaGadgets_Api.factura.dto.FacturaResponseDTO;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.factura.service.FacturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FacturaController {
    @Autowired
    FacturaService facturaService;

    @PutMapping("/facturas/{id}")
    public ResponseEntity<FacturaResponseDTO> updateFactura(@PathVariable("id")Integer id, @Valid @RequestBody FacturaRequestDTO facturaRequestDTO) {
        return new ResponseEntity<FacturaResponseDTO>(facturaService.updateFactura(id,facturaRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/facturas")
    public ResponseEntity<List<FacturaEntity>> getAllFacturas(){
        return new ResponseEntity<List<FacturaEntity>>(facturaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/facturas/state/{state}")
    public ResponseEntity<List<FacturaEntity>> getAllFacturasByState(@PathVariable("state") String state){
        return new ResponseEntity<List<FacturaEntity>>(facturaService.getByState(state), HttpStatus.OK);
    }

    @DeleteMapping("/facturas/{id}")
    public ResponseEntity<String> deleteFactura(@PathVariable("id")Integer id){
        facturaService.deleteFactura(id);
        return new ResponseEntity<>("Factura deleted successfull", HttpStatus.OK);
    }

    @GetMapping("/facturas/user/{id}")
    public ResponseEntity<List<FacturaEntity>> getFacturasByUserId(@PathVariable("id") Integer id){
        return new ResponseEntity<List<FacturaEntity>>(facturaService.findFacturasByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/facturas/user/{id}/state/{state}")
    public ResponseEntity<List<FacturaEntity>> getFacturasByUserAndState(@PathVariable("id") Integer id,@PathVariable("state") String state){
        return new ResponseEntity<List<FacturaEntity>>(facturaService.findFacturasByUserIdAndState(id,state), HttpStatus.OK);
    }
}
