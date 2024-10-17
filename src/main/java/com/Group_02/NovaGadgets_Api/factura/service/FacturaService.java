package com.Group_02.NovaGadgets_Api.factura.service;

import com.Group_02.NovaGadgets_Api.factura.dto.FacturaRequestDTO;
import com.Group_02.NovaGadgets_Api.factura.dto.FacturaResponseDTO;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;

import java.util.List;

public interface FacturaService {
    public abstract void addFactura(Double totalInvoiced, OrderEntity order);
    public abstract FacturaResponseDTO updateFactura(Integer id,  FacturaRequestDTO facturaRequestDTO);
    public abstract void deleteFactura(Integer id);
    public abstract FacturaEntity getFacturaById(Integer id);
    public abstract List<FacturaEntity> getAll();
    public abstract List<FacturaEntity> getByState(String state);
    public abstract List<FacturaEntity> findFacturasByUserId(Integer id);
    public abstract List<FacturaEntity> findFacturasByUserIdAndState(Integer id, String state);
}
