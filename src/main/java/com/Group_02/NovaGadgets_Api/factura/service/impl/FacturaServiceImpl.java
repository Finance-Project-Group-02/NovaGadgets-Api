package com.Group_02.NovaGadgets_Api.factura.service.impl;

import com.Group_02.NovaGadgets_Api.factura.dto.FacturaRequestDTO;
import com.Group_02.NovaGadgets_Api.factura.dto.FacturaResponseDTO;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.factura.repository.FacturaRepository;
import com.Group_02.NovaGadgets_Api.factura.service.FacturaService;
import com.Group_02.NovaGadgets_Api.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public FacturaResponseDTO addFactura(FacturaRequestDTO facturaRequestDTO) {
        Double totalInvoiced = facturaRequestDTO.getTotalInvoiced();
        Integer days = calcularNumeroDias(facturaRequestDTO.getDiscountDate(), facturaRequestDTO.getPaymentDate());
        Double nuevaTasaEfectiva = calcularNuevaTasaEfectiva(facturaRequestDTO.getEffectiveRate(), facturaRequestDTO.getRateTerm(), days);
        nuevaTasaEfectiva = redondear(nuevaTasaEfectiva,7);
        Double tasaDescontada = calcularTasaDescontada(nuevaTasaEfectiva);
        tasaDescontada = redondear(tasaDescontada,7);
        Double discount = facturaRequestDTO.getTotalInvoiced()*tasaDescontada/100;
        discount = redondear(discount,2);
        Double netWorth = totalInvoiced - discount;
        netWorth = redondear(netWorth,2);
        Double initialCosts = 0.0;
        for (Double cost : facturaRequestDTO.getInitialCosts()) {
            initialCosts += cost;
        }

        Double finalCosts = 0.0;
        for (Double cost : facturaRequestDTO.getFinalCosts()) {
            finalCosts += cost;
        }

        Double valueReceived = netWorth - initialCosts - facturaRequestDTO.getRetention();
        Double valueDelivered = totalInvoiced + finalCosts - facturaRequestDTO.getRetention();

        valueReceived = redondear(valueReceived,2);
        valueDelivered = redondear(valueDelivered,2);
        Double tcea = calcularTCEA(valueReceived,valueDelivered, days);
        tcea = redondear(tcea,7);

        FacturaResponseDTO facturaResponseDTO = new FacturaResponseDTO(facturaRequestDTO.getStartDate(), totalInvoiced, facturaRequestDTO.getPaymentDate(),
                days, facturaRequestDTO.getRetention(), nuevaTasaEfectiva, tasaDescontada, discount, initialCosts, finalCosts,
                netWorth, valueReceived, valueDelivered, tcea);

        FacturaEntity factura = new FacturaEntity(0, facturaRequestDTO.getState(), facturaRequestDTO.getStartDate(), facturaRequestDTO.getPaymentDate(), facturaRequestDTO.getDiscountDate(),
                facturaRequestDTO.getRetention(),facturaRequestDTO.getEffectiveRate(), facturaRequestDTO.getRateTerm(), facturaRequestDTO.getDayByYear(),
                totalInvoiced, initialCosts, finalCosts, days, nuevaTasaEfectiva, tasaDescontada, discount, netWorth,
                valueDelivered, valueReceived, tcea);

        facturaRepository.save(factura);
        return facturaResponseDTO;
    }

    public Integer calcularNumeroDias(LocalDate discountDate, LocalDate paymentDate){
        Integer daysBetween =  (int) ChronoUnit.DAYS.between(discountDate, paymentDate);
        return daysBetween;
    }

    public Double calcularNuevaTasaEfectiva(Double effectiveRate, Integer rateTerm, Integer days){
        Double division = (double) days / (double) rateTerm;
        Double parte = (1+effectiveRate/100);
        Double nuevaTasaEfectiva = Math.pow(parte,division) - 1;
        return nuevaTasaEfectiva * 100;
    }

    public Double calcularTasaDescontada(Double nuevaTasaEfectiva){
        Double tasaDescontada = ((nuevaTasaEfectiva/100)/(1+(nuevaTasaEfectiva/100)));
        return  tasaDescontada*100;
    }

    public Double calcularTCEA(Double valueReceived, Double valueDelivered, Integer days){
        Double tcea = Math.pow((valueDelivered/valueReceived), (360/(double)days));
        tcea = tcea -1;
        return  tcea*100;
    }

    public Double redondear(Double valor, int decimales) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    @Override
    public void deleteFactura(Integer id) {
        if (!facturaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontro la factura");
        }
        facturaRepository.deleteById(id);
    }

    @Override
    public List<FacturaEntity> getAll() {
        return facturaRepository.findAll();
    }

    @Override
    public List<FacturaEntity> getByState(String state) {
        return facturaRepository.findByState(state);
    }
}
