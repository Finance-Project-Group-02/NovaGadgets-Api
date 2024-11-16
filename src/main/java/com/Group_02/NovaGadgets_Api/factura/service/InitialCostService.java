package com.Group_02.NovaGadgets_Api.factura.service;

import com.Group_02.NovaGadgets_Api.factura.dto.CostDTO;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.factura.model.InitialCostEntity;

public interface InitialCostService {
    public InitialCostEntity addInitialCost(CostDTO costDTO, FacturaEntity factura);
}
