package com.Group_02.NovaGadgets_Api.order.service.impl;

import com.Group_02.NovaGadgets_Api.factura.service.FacturaService;
import com.Group_02.NovaGadgets_Api.order.dto.OrderDTO;
import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;
import com.Group_02.NovaGadgets_Api.order.repository.OrderRepository;
import com.Group_02.NovaGadgets_Api.order.service.OrderService;
import com.Group_02.NovaGadgets_Api.shared.exception.ResourceNotFoundException;
import com.Group_02.NovaGadgets_Api.user.model.UsersEntity;
import com.Group_02.NovaGadgets_Api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FacturaService facturaService;

    @Override
    public OrderEntity addOrder(OrderDTO orderDTO) {
        UsersEntity userFound = userRepository.findById(orderDTO.getIdUser()).orElse(null);
        if(userFound == null){
            throw new ResourceNotFoundException("User no encontrado");
        }
        OrderEntity order = new OrderEntity(0, orderDTO.getOrderDate(),userFound);
        order = orderRepository.save(order);
        facturaService.addFactura(orderDTO.getTotalInvoiced(),order);
        return order;
    }

    @Override
    public OrderEntity getOrderById(Integer id) {
        OrderEntity order = orderRepository.findById(id).orElse(null);
        if(order == null){
            throw new ResourceNotFoundException("Order no encontrado");
        }
        return order;
    }

    @Override
    public List<OrderEntity> getOrdersByClient(Integer id) {
        UsersEntity userFound = userRepository.findById(id).orElse(null);
        if(userFound == null){
            throw new ResourceNotFoundException("User no encontrado");
        }
        List<OrderEntity> orders = orderRepository.findAll();
        List<OrderEntity> ordersByUser = new ArrayList<>();
        for(OrderEntity order: orders){
            if(order.getUser().getId() == id){
                ordersByUser.add(order);
            }
        }
        return ordersByUser;
    }

    @Override
    public List<OrderEntity> getOrdersByDate(LocalDate startDate, LocalDate endDate) {
        List<OrderEntity> orders = orderRepository.getOrdersByDate(startDate,endDate);
        return orders;
    }

    @Override
    public List<OrderEntity> getOrdersByClientAndDate(Integer id, LocalDate startDate, LocalDate endDate) {
        UsersEntity userFound = userRepository.findById(id).orElse(null);
        if(userFound == null){
            throw new ResourceNotFoundException("User no encontrado");
        }

        List<OrderEntity> orders = orderRepository.getOrdersByDate(startDate,endDate);
        List<OrderEntity> ordersByUser = new ArrayList<>();
        for(OrderEntity order: orders){
            if(order.getUser().getId() == id){
                ordersByUser.add(order);
            }
        }
        return ordersByUser;
    }
}
