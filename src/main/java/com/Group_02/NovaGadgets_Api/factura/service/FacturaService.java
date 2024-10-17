package com.Group_02.NovaGadgets_Api.factura.service;

import com.Group_02.NovaGadgets_Api.factura.dto.FacturaRequestDTO;
import com.Group_02.NovaGadgets_Api.factura.dto.FacturaResponseDTO;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;

import java.util.List;

public interface FacturaService {
    public abstract FacturaResponseDTO addFactura(FacturaRequestDTO facturaRequestDTO);
    public abstract void deleteFactura(Integer id);
    public abstract List<FacturaEntity> getAll();
    public abstract List<FacturaEntity> getByState(String state);

}
