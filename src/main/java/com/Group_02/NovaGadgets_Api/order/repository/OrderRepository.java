package com.Group_02.NovaGadgets_Api.order.repository;

import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM orders o " +
            "WHERE o.order_date BETWEEN :startDate AND :endDate")
    public List<OrderEntity> getOrdersByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
