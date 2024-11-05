package com.Group_02.NovaGadgets_Api.orderDetail.controller;

import com.Group_02.NovaGadgets_Api.order.dto.OrderDTO;
import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;
import com.Group_02.NovaGadgets_Api.orderDetail.dto.OrderDetailDTO;
import com.Group_02.NovaGadgets_Api.orderDetail.model.OrderDetailEntity;
import com.Group_02.NovaGadgets_Api.orderDetail.service.OrderDetailService;
import com.Group_02.NovaGadgets_Api.productStore.model.ProductStoreEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;

    @PostMapping("/orderDetail/orderId/{orderId}")
    public ResponseEntity<OrderDetailEntity> addOrder (@Valid @RequestBody OrderDetailDTO orderDetailDTO, @PathVariable("orderId")Integer orderId) {
        return new ResponseEntity<OrderDetailEntity>(orderDetailService.addOrderDetail(orderId, orderDetailDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/orderDetail/id/{id}")
    public ResponseEntity<String> deleteOrderProduct(@PathVariable("id")Integer id){
        orderDetailService.deleteOrderDetail(id);
        return new ResponseEntity<>("OrderDetail deleted successfull", HttpStatus.OK);
    }

    @GetMapping("/orderDetail/orderId/{orderId}")
    public ResponseEntity<List<OrderDetailDTO>> listOrderDetailByOrderId(@PathVariable("orderId")Integer orderId){
        return new ResponseEntity<List<OrderDetailDTO>>(orderDetailService.listOrderDetailByOrderId(orderId), HttpStatus.OK);
    }

    @PutMapping("/orderDetail/id/{id}")
    public ResponseEntity<OrderDetailEntity> updateOrderDetail(@PathVariable("id")Integer id, @Valid @RequestBody OrderDetailDTO orderDetailDTO){
        return new ResponseEntity<OrderDetailEntity>(orderDetailService.updateOrderDetail(id, orderDetailDTO), HttpStatus.OK);
    }
}
