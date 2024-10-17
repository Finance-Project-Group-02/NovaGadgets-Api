package com.Group_02.NovaGadgets_Api.factura.repository;

import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacturaRepository extends JpaRepository<FacturaEntity,Integer> {
    boolean existsById(int id);
    List<FacturaEntity> findByState(String state);
    @Query(nativeQuery = true, value = "" +
            "SELECT f.* FROM facturas f " +
            "INNER JOIN orders o ON o.id = f.order_id " +
            "WHERE o.user_id = :userId")
    List<FacturaEntity> findFacturasByUserId(@Param("userId") Integer userId);

    @Query(nativeQuery = true, value = "" +
            "SELECT f.* FROM facturas f " +
            "INNER JOIN orders o ON o.id = f.order_id " +
            "WHERE o.user_id = :userId " +
            "AND f.state = :state")
    List<FacturaEntity> findFacturasByUserIdAndState(@Param("userId") Integer userId, @Param("state") String state);
}
