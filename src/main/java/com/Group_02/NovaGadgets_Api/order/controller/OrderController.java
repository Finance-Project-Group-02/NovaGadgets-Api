package com.Group_02.NovaGadgets_Api.order.controller;

import com.Group_02.NovaGadgets_Api.order.dto.OrderDTO;
import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;
import com.Group_02.NovaGadgets_Api.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderEntity> addOrder (@Valid @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<OrderEntity>(orderService.addOrder(orderDTO), HttpStatus.CREATED);
    }

    @GetMapping("/order/userId/{id}")
    public ResponseEntity<List<OrderEntity>> getOrdersByClient (@PathVariable("id") Integer id) {
        return new ResponseEntity<List<OrderEntity>>(orderService.getOrdersByClient(id), HttpStatus.OK);
    }

    @GetMapping("/order/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<List<OrderEntity>> getOrdersByDate (@PathVariable("startDate") LocalDate startDate,@PathVariable("endDate") LocalDate endDate) {
        return new ResponseEntity<List<OrderEntity>>(orderService.getOrdersByDate(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/order/userId/{id}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<List<OrderEntity>> getOrdersByClient (@PathVariable("id") Integer id, @PathVariable("startDate") LocalDate startDate,@PathVariable("endDate") LocalDate endDate) {
        return new ResponseEntity<List<OrderEntity>>(orderService.getOrdersByClientAndDate(id,startDate,endDate), HttpStatus.OK);
    }
}
