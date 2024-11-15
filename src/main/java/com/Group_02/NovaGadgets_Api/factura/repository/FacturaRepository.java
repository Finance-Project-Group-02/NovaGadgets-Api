package com.Group_02.NovaGadgets_Api.factura.repository;

import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    @Query(nativeQuery = true, value = "" +
            "SELECT f.* FROM facturas f " +
            "WHERE f.state = :state " +
            "AND f.discount_date = :discountDate " +
            "AND f.day_by_year = :dayByYear " +
            "AND f.effective_rate = :effectiveRate " +
            "AND f.rate_term = :rateTerm " +
            "AND f.initial_costs = :initialCosts " +
            "AND f.final_costs = :finalCosts")
    List<FacturaEntity> findFacturasCartera(@Param("state") String state,
                                            @Param("dayByYear") Integer dayByYear,
                                            @Param("discountDate") LocalDate discountDate,
                                            @Param("effectiveRate") Double effectiveRate,
                                            @Param("rateTerm") Integer rateTerm,
                                            @Param("initialCosts") Double initialCosts,
                                            @Param("finalCosts") Double finalCosts);
}
