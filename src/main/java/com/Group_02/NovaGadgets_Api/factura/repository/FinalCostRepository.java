package com.Group_02.NovaGadgets_Api.factura.repository;

import com.Group_02.NovaGadgets_Api.factura.model.FinalCostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinalCostRepository extends JpaRepository<FinalCostEntity, Integer> {
    List<FinalCostEntity> findByFactura_id(Integer factura_id);
}
