package com.Group_02.NovaGadgets_Api.factura.repository;

import com.Group_02.NovaGadgets_Api.factura.model.InitialCostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InitialCostRepository extends JpaRepository<InitialCostEntity, Integer> {
    List<InitialCostEntity> findByFactura_id(Integer factura_id);

}
