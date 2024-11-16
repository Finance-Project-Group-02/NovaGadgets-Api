package com.Group_02.NovaGadgets_Api.factura.service.impl;

import com.Group_02.NovaGadgets_Api.factura.dto.CostDTO;
import org.springframework.context.annotation.Lazy;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.factura.model.InitialCostEntity;
import com.Group_02.NovaGadgets_Api.factura.repository.InitialCostRepository;
import com.Group_02.NovaGadgets_Api.factura.service.FacturaService;
import com.Group_02.NovaGadgets_Api.factura.service.InitialCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitialCostServiceImpl implements InitialCostService {
    @Autowired
    InitialCostRepository initialCostRepository;

    @Override
    public InitialCostEntity addInitialCost(CostDTO costDTO,FacturaEntity factura) {
        InitialCostEntity initialCostEntity = new InitialCostEntity(0,costDTO.getName(),
                costDTO.getType(),costDTO.getValue(),factura);
        return initialCostRepository.save(initialCostEntity);
    }
}
