package com.Group_02.NovaGadgets_Api.factura.service;

import com.Group_02.NovaGadgets_Api.factura.dto.CostDTO;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.factura.model.FinalCostEntity;
import com.Group_02.NovaGadgets_Api.factura.model.InitialCostEntity;

public interface FinalCostService {
    public FinalCostEntity addFinalCost(CostDTO costDTO, FacturaEntity factura);
}
