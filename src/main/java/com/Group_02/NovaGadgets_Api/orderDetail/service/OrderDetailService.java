package com.Group_02.NovaGadgets_Api.orderDetail.service;

import com.Group_02.NovaGadgets_Api.orderDetail.dto.OrderDetailDTO;
import com.Group_02.NovaGadgets_Api.orderDetail.model.OrderDetailEntity;

import java.util.List;

public interface OrderDetailService {
    public void deleteOrderDetail(Integer id);
    public OrderDetailEntity addOrderDetail(Integer orderId, OrderDetailDTO orderDetailDTO);
    public List<OrderDetailDTO> listOrderDetailByOrderId(Integer orderId);
    public OrderDetailEntity updateOrderDetail(Integer orderDetailId, OrderDetailDTO orderDetailDTO);
}
