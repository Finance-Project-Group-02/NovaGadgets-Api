package com.Group_02.NovaGadgets_Api.factura.repository;

import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaRepository extends JpaRepository<FacturaEntity,Integer> {
    boolean existsById(int id);
    List<FacturaEntity> findByState(String state);
}
