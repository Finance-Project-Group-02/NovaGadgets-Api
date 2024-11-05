package com.Group_02.NovaGadgets_Api.orderDetail.repository;

import com.Group_02.NovaGadgets_Api.orderDetail.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
    public boolean existsByProduct_idAndOrder_id(Integer product_id, Integer order_id);
    public List<OrderDetailEntity> findByOrder_id(Integer order_id);
    public List<OrderDetailEntity> findByProduct_idAndOrder_id(Integer product_id, Integer order_id);
}
