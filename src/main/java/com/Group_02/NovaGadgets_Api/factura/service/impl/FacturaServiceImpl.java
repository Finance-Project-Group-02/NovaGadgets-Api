package com.Group_02.NovaGadgets_Api.factura.service.impl;

import com.Group_02.NovaGadgets_Api.factura.dto.FacturaRequestDTO;
import com.Group_02.NovaGadgets_Api.factura.dto.FacturaResponseDTO;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.factura.repository.FacturaRepository;
import com.Group_02.NovaGadgets_Api.factura.service.FacturaService;
import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;
import com.Group_02.NovaGadgets_Api.order.repository.OrderRepository;
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

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addFactura(Double totalInvoiced, OrderEntity order) {
        FacturaEntity factura = new FacturaEntity();
        factura.setState("PENDIENTE");
        factura.setTotalInvoiced(totalInvoiced);
        factura.setOrder(order);
        facturaRepository.save(factura);
    }

    @Override
    public FacturaResponseDTO updateFactura(Integer id, FacturaRequestDTO facturaRequestDTO) {
        FacturaEntity facturaFound = getFacturaById(id);

        Double totalInvoiced = facturaFound.getTotalInvoiced();
        Integer days = calcularNumeroDias(facturaRequestDTO.getDiscountDate(), facturaRequestDTO.getPaymentDate());
        Double nuevaTasaEfectiva = calcularNuevaTasaEfectiva(facturaRequestDTO.getEffectiveRate(), facturaRequestDTO.getRateTerm(), days);
        nuevaTasaEfectiva = redondear(nuevaTasaEfectiva,7);
        Double tasaDescontada = calcularTasaDescontada(nuevaTasaEfectiva);
        tasaDescontada = redondear(tasaDescontada,7);
        Double discount = totalInvoiced*tasaDescontada/100;
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


        facturaFound.setState(facturaRequestDTO.getState());
        facturaFound.setStartDate(facturaRequestDTO.getStartDate());
        facturaFound.setPaymentDate(facturaRequestDTO.getPaymentDate());
        facturaFound.setDiscountDate(facturaRequestDTO.getDiscountDate());
        facturaFound.setRetention(facturaRequestDTO.getRetention());
        facturaFound.setEffectiveRate(facturaRequestDTO.getEffectiveRate());
        facturaFound.setRateTerm(facturaRequestDTO.getRateTerm());
        facturaFound.setDayByYear(facturaRequestDTO.getDayByYear());
        facturaFound.setInitialCosts(initialCosts);
        facturaFound.setFinalCosts(finalCosts);
        facturaFound.setDays(days);
        facturaFound.setNewEffectiveRate(nuevaTasaEfectiva);
        facturaFound.setDiscountedRate(tasaDescontada);
        facturaFound.setDiscount(discount);
        facturaFound.setNetWorth(netWorth);
        facturaFound.setValueDelivered(valueDelivered);
        facturaFound.setValueReceived(valueReceived);
        facturaFound.setTcea(tcea);

        facturaRepository.save(facturaFound);

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
    public FacturaEntity getFacturaById(Integer id) {
        FacturaEntity factura = facturaRepository.findById(id).orElse(null);
        if (factura==null) {
            throw new ResourceNotFoundException("Factura no encontrado");
        }
        return factura;
    }

    @Override
    public List<FacturaEntity> getAll() {
        return facturaRepository.findAll();
    }

    @Override
    public List<FacturaEntity> getByState(String state) {
        return facturaRepository.findByState(state);
    }

    @Override
    public List<FacturaEntity> findFacturasByUserId(Integer id) {
        return facturaRepository.findFacturasByUserId(id);
    }

    @Override
    public List<FacturaEntity> findFacturasByUserIdAndState(Integer id, String state) {
        return facturaRepository.findFacturasByUserIdAndState(id,state);
    }
}
