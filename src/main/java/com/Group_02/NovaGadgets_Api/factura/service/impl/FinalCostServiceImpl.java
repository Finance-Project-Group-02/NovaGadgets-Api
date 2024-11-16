package com.Group_02.NovaGadgets_Api.factura.service.impl;

import com.Group_02.NovaGadgets_Api.factura.dto.CostDTO;
import org.springframework.context.annotation.Lazy;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.factura.model.FinalCostEntity;
import com.Group_02.NovaGadgets_Api.factura.repository.FinalCostRepository;
import com.Group_02.NovaGadgets_Api.factura.service.FacturaService;
import com.Group_02.NovaGadgets_Api.factura.service.FinalCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalCostServiceImpl implements FinalCostService {

    @Autowired
    FinalCostRepository finalCostRepository;

    @Override
    public FinalCostEntity addFinalCost(CostDTO costDTO, FacturaEntity factura) {
        FinalCostEntity finalCostEntity = new FinalCostEntity(0,costDTO.getName(),
                costDTO.getType(),costDTO.getValue(),factura);
        return finalCostRepository.save(finalCostEntity);
    }
}
