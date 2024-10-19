package com.Group_02.NovaGadgets_Api.order.service;

import com.Group_02.NovaGadgets_Api.order.dto.OrderDTO;
import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    public abstract OrderEntity addOrder(OrderDTO orderDTO);
    public abstract OrderEntity getOrderById(Integer id);
    public abstract List<OrderEntity> getOrdersByClient(Integer id);
    public abstract List<OrderEntity> getOrdersByDate(LocalDate startDate, LocalDate endDate);
    public abstract List<OrderEntity> getOrdersByClientAndDate(Integer id, LocalDate startDate, LocalDate endDate);
}
