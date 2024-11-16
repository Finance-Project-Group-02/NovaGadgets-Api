package com.Group_02.NovaGadgets_Api.factura.service.impl;

import com.Group_02.NovaGadgets_Api.factura.dto.*;
import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.Group_02.NovaGadgets_Api.factura.model.FinalCostEntity;
import com.Group_02.NovaGadgets_Api.factura.model.InitialCostEntity;
import com.Group_02.NovaGadgets_Api.factura.repository.FacturaRepository;
import com.Group_02.NovaGadgets_Api.factura.repository.FinalCostRepository;
import com.Group_02.NovaGadgets_Api.factura.repository.InitialCostRepository;
import com.Group_02.NovaGadgets_Api.factura.service.FacturaService;
import com.Group_02.NovaGadgets_Api.factura.service.FinalCostService;
import com.Group_02.NovaGadgets_Api.factura.service.InitialCostService;
import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;
import com.Group_02.NovaGadgets_Api.order.repository.OrderRepository;
import com.Group_02.NovaGadgets_Api.shared.exception.ResourceNotFoundException;
import com.Group_02.NovaGadgets_Api.user.model.UsersEntity;
import com.Group_02.NovaGadgets_Api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InitialCostService initialCostService;

    @Autowired
    private FinalCostService finalCostService;

    @Autowired
    private InitialCostRepository initialCostRepository;

    @Autowired
    private FinalCostRepository finalCostRepository;

    @Override
    public void addFactura(Double totalInvoiced, OrderEntity order) {
        FacturaEntity factura = new FacturaEntity();
        factura.setState("PENDIENTE");
        factura.setTotalInvoiced(totalInvoiced);
        factura.setNominalValue(redondear(totalInvoiced*0.82,2));
        factura.setOrder(order);
        facturaRepository.save(factura);
    }

    @Override
    public FacturaResponseDTO updateFactura(Integer id, FacturaRequestDTO facturaRequestDTO) {
        FacturaEntity facturaFound = getFacturaById(id);

        Double totalInvoiced = facturaFound.getTotalInvoiced();
        Double nominalValue = facturaFound.getNominalValue();
        Integer dayYear = facturaRequestDTO.getDayByYear();
        Integer days = calcularNumeroDias(facturaRequestDTO.getDiscountDate(), facturaRequestDTO.getPaymentDate());
        String type = facturaRequestDTO.getType();

        Double nuevaTasaEfectiva = 0.0;
        if(type.equals("E")){
            nuevaTasaEfectiva = calcularNuevaTasaEfectiva(facturaRequestDTO.getEffectiveRate(), facturaRequestDTO.getRateTerm(), days);
        }
        if(type.equals("N")){
            nuevaTasaEfectiva = calcularNuevaTasaEfectivaDeNominal(facturaRequestDTO.getEffectiveRate(), facturaRequestDTO.getRateTerm(),
                    days,facturaRequestDTO.getCapitalization());
        }
        nuevaTasaEfectiva = redondear(nuevaTasaEfectiva,7);
        Double tasaDescontada = calcularTasaDescontada(nuevaTasaEfectiva);
        tasaDescontada = redondear(tasaDescontada,7);
        Double discount = nominalValue*tasaDescontada/100;
        discount = redondear(discount,2);
        Double netWorth = nominalValue - discount;
        netWorth = redondear(netWorth,2);
        Double initialCosts = 0.0;

        for (CostDTO costDTO : facturaRequestDTO.getInitialCosts()) {
            initialCostService.addInitialCost(costDTO,facturaFound);
            if(costDTO.getType().equals("E")){
                initialCosts += costDTO.getValue();
            }
            else{
                initialCosts += ((costDTO.getValue()/100)*nominalValue);
            }

        }
        initialCosts = redondear(initialCosts,2);

        Double finalCosts = 0.0;
        for (CostDTO costDTO : facturaRequestDTO.getFinalCosts()) {
            finalCostService.addFinalCost(costDTO,facturaFound);
            if(costDTO.getType().equals("E")){
                finalCosts += costDTO.getValue();
            }
            else{
                finalCosts += ((costDTO.getValue()/100)*nominalValue);
            }
        }
        finalCosts = redondear(finalCosts,2);

        Double valueReceived = netWorth - initialCosts - facturaRequestDTO.getRetention();
        Double valueDelivered = nominalValue + finalCosts - facturaRequestDTO.getRetention();

        valueReceived = redondear(valueReceived,2);
        valueDelivered = redondear(valueDelivered,2);
        Double tcea = calcularTCEA(valueReceived,valueDelivered, days,dayYear);
        tcea = redondear(tcea,7);

        FacturaResponseDTO facturaResponseDTO = new FacturaResponseDTO(facturaRequestDTO.getStartDate(), totalInvoiced,nominalValue, facturaRequestDTO.getPaymentDate(),
                days, facturaRequestDTO.getRetention(), nuevaTasaEfectiva, tasaDescontada, discount, initialCosts, finalCosts,
                netWorth, valueDelivered, valueReceived, tcea);


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

    public Double calcularNuevaTasaEfectivaDeNominal(Double effectiveRate, Integer rateTerm, Integer days, Integer capitalization){
        Double m = (double) rateTerm / capitalization;
        Double n = (double) days / capitalization;
        Double division = (effectiveRate / 100) / m;
        Double nuevaTasaEfectiva = Math.pow(1 + division, n) - 1;
        return nuevaTasaEfectiva * 100;
    }

    public Double calcularTasaDescontada(Double nuevaTasaEfectiva){
        Double tasaDescontada = ((nuevaTasaEfectiva/100)/(1+(nuevaTasaEfectiva/100)));
        return  tasaDescontada*100;
    }

    public Double calcularTCEA(Double valueReceived, Double valueDelivered, Integer days, Integer dayByYear){
        Double tcea = Math.pow((valueDelivered/valueReceived), ((double)dayByYear/(double)days));
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
    public List<FacturaSummaryDTO> getByState(String state) {
        List<FacturaEntity> list = facturaRepository.findByState(state);
        List<FacturaSummaryDTO> listSummary = new ArrayList<>();
        for(FacturaEntity fact : list){
            FacturaSummaryDTO facturaSummaryDTO = new FacturaSummaryDTO(fact.getId(), fact.getState(),
                    fact.getOrder().getUser().getUsername(), fact.getStartDate(),fact.getDiscountDate(),fact.getTotalInvoiced(), fact.getNominalValue(),fact.getPaymentDate(), fact.getDays(),
                    fact.getRetention(),fact.getNewEffectiveRate(),fact.getDiscountedRate(), fact.getDiscount(),fact.getInitialCosts(),
                    fact.getFinalCosts(),fact.getNetWorth(),fact.getValueDelivered(),fact.getValueReceived(),fact.getTcea(),fact.getStartDate());
            listSummary.add(facturaSummaryDTO);
        }
        return listSummary;
    }

    @Override
    public List<FacturaSummaryDTO> findFacturasByUserId(Integer id){
        List<FacturaEntity> list = facturaRepository.findFacturasByUserId(id);
        List<FacturaSummaryDTO> listSummary = new ArrayList<>();
        for(FacturaEntity fact : list){
            FacturaSummaryDTO facturaSummaryDTO = new FacturaSummaryDTO(fact.getId(), fact.getState(),
                    fact.getOrder().getUser().getUsername(), fact.getOrder().getOrderDate(),fact.getDiscountDate(),fact.getTotalInvoiced(), fact.getNominalValue(),fact.getPaymentDate(), fact.getDays(),
                    fact.getRetention(),fact.getNewEffectiveRate(),fact.getDiscountedRate(), fact.getDiscount(),fact.getInitialCosts(),
                    fact.getFinalCosts(),fact.getNetWorth(),fact.getValueDelivered(),fact.getValueReceived(),fact.getTcea(),fact.getStartDate());
            listSummary.add(facturaSummaryDTO);
        }
        return listSummary;
    }

    @Override
    public List<FacturaEntity> findFacturasByUserIdAndState(Integer id, String state) {
        return facturaRepository.findFacturasByUserIdAndState(id,state);
    }

    @Override
    public FacturaResponseDTO simularFactura(Integer id, FacturaRequestDTO facturaRequestDTO) {
        FacturaEntity facturaFound = getFacturaById(id);

        Integer dayYear = facturaRequestDTO.getDayByYear();
        Double totalInvoiced = facturaFound.getTotalInvoiced();
        Double nominalValue = facturaFound.getNominalValue();
        Integer days = calcularNumeroDias(facturaRequestDTO.getDiscountDate(), facturaRequestDTO.getPaymentDate());
        String type = facturaRequestDTO.getType();

        Double nuevaTasaEfectiva = 0.0;
        if(type.equals("E")){
            nuevaTasaEfectiva = calcularNuevaTasaEfectiva(facturaRequestDTO.getEffectiveRate(), facturaRequestDTO.getRateTerm(), days);
        }
        if(type.equals("N")){
            nuevaTasaEfectiva = calcularNuevaTasaEfectivaDeNominal(facturaRequestDTO.getEffectiveRate(), facturaRequestDTO.getRateTerm(),
                    days,facturaRequestDTO.getCapitalization());
        }
        nuevaTasaEfectiva = redondear(nuevaTasaEfectiva,7);
        Double tasaDescontada = calcularTasaDescontada(nuevaTasaEfectiva);
        tasaDescontada = redondear(tasaDescontada,7);
        Double discount = nominalValue*tasaDescontada/100;
        discount = redondear(discount,2);
        Double netWorth = nominalValue - discount;
        netWorth = redondear(netWorth,2);
        Double initialCosts = 0.0;

        for (CostDTO costDTO : facturaRequestDTO.getInitialCosts()) {
            if(costDTO.getType().equals("E")){
                initialCosts += costDTO.getValue();
            }
            else{
                initialCosts += ((costDTO.getValue()/100)*nominalValue);
            }

        }
        initialCosts = redondear(initialCosts,2);
        Double finalCosts = 0.0;
        for (CostDTO costDTO : facturaRequestDTO.getFinalCosts()) {
            if(costDTO.getType().equals("E")){
                finalCosts += costDTO.getValue();
            }
            else{
                finalCosts += ((costDTO.getValue()/100)*nominalValue);
            }
        }
        finalCosts = redondear(finalCosts,2);
        Double valueReceived = netWorth - initialCosts - facturaRequestDTO.getRetention();
        Double valueDelivered = nominalValue + finalCosts - facturaRequestDTO.getRetention();

        valueReceived = redondear(valueReceived,2);
        valueDelivered = redondear(valueDelivered,2);
        Double tcea = calcularTCEA(valueReceived,valueDelivered, days,dayYear);
        tcea = redondear(tcea,7);

        FacturaResponseDTO facturaResponseDTO = new FacturaResponseDTO(facturaRequestDTO.getStartDate(), totalInvoiced,nominalValue, facturaRequestDTO.getPaymentDate(),
                days, facturaRequestDTO.getRetention(), nuevaTasaEfectiva, tasaDescontada, discount, initialCosts, finalCosts,
                netWorth, valueDelivered, valueReceived, tcea);

        return  facturaResponseDTO;
    }

    @Override
    public List<FacturaSummaryDTO> getAllSummary() {
        List<FacturaEntity> list =facturaRepository.findAll();
        List<FacturaSummaryDTO> summaries = new ArrayList<>();

        for(FacturaEntity factura : list){
            OrderEntity order = orderRepository.findById(factura.getOrder().getId()).orElse(null);
            UsersEntity user = userRepository.findById(order.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

            FacturaSummaryDTO facturaSummaryDTO = new FacturaSummaryDTO(factura.getId(), factura.getState(),
                    user.getUsername(), order.getOrderDate(),factura.getDiscountDate(),factura.getTotalInvoiced(), factura.getNominalValue(),factura.getPaymentDate(), factura.getDays(),
                    factura.getRetention(),factura.getNewEffectiveRate(),factura.getDiscountedRate(), factura.getDiscount(),factura.getInitialCosts(),
                    factura.getFinalCosts(),factura.getNetWorth(),factura.getValueDelivered(),factura.getValueReceived(),factura.getTcea(),factura.getStartDate());

            summaries.add(facturaSummaryDTO);
        }
        return summaries;
    }

    @Override
    public FacturaSummaryDTO getFacturaSummary(Integer id) {
        FacturaEntity factura = getFacturaById(id);
        OrderEntity order = orderRepository.findById(factura.getOrder().getId()).orElse(null);
        UsersEntity user = userRepository.findById(order.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        FacturaSummaryDTO facturaSummaryDTO = new FacturaSummaryDTO(factura.getId(), factura.getState(),
                user.getUsername(), order.getOrderDate(),factura.getDiscountDate(),factura.getTotalInvoiced(), factura.getNominalValue(),factura.getPaymentDate(), factura.getDays(),
                factura.getRetention(),factura.getNewEffectiveRate(),factura.getDiscountedRate(), factura.getDiscount(),factura.getInitialCosts(),
                factura.getFinalCosts(),factura.getNetWorth(),factura.getValueDelivered(),factura.getValueReceived(),factura.getTcea(),factura.getStartDate());

        return facturaSummaryDTO;
    }

    @Override
    public TCEACarteraDTO getTCEACartera(List<Integer> idFacturas) {
        Double totalValueReceived = 0.0;
        List<Double> flujos = new ArrayList<>();
        List<Integer> dias = new ArrayList<>();
        Integer dayByYear = getFacturaById(idFacturas.get(0)).getDayByYear();
        double precision = 0.00000001;

        for (Integer id : idFacturas) {
            FacturaEntity factura = getFacturaById(id);
            totalValueReceived += factura.getValueReceived();
        }

        for (Integer id : idFacturas) {
            FacturaEntity factura = getFacturaById(id);
            flujos.add(factura.getValueReceived() * -1);
            flujos.add(factura.getValueDelivered());
            dias.add(0);
            dias.add(factura.getDays());
        }

        double tirmMin = 0.000001;
        double tirmMax = 1.0;
        double tirmMedio;
        int maxIteraciones = 10000;
        int iteracion = 0;
        double result = 0.0;

        while (iteracion < maxIteraciones) {
            tirmMedio = (tirmMin + tirmMax) / 2.0;
            double flujoDescontado = 0.0;

            for (int i = 0; i < flujos.size(); i++) {
                flujoDescontado += flujos.get(i) / Math.pow(1 + tirmMedio, (double) dias.get(i) / (double)dayByYear);
            }

            if (Math.abs(flujoDescontado) < precision) {
                result = tirmMedio * 100;
                break;
            }

            if (flujoDescontado > 0) {
                tirmMin = tirmMedio;
            } else {
                tirmMax = tirmMedio;
            }

            iteracion++;
        }

        TCEACarteraDTO tceaCarteraDTO = new TCEACarteraDTO(redondear(totalValueReceived,2),redondear(result,7));
        return tceaCarteraDTO;
    }

    public boolean tienenMismosFinalCosts(FacturaEntity factura1, FacturaEntity factura2) {
        // Obtener los costos finales de ambas facturas
        List<FinalCostEntity> finalCosts1 = finalCostRepository.findByFactura_id(factura1.getId());
        List<FinalCostEntity> finalCosts2 = finalCostRepository.findByFactura_id(factura2.getId());

        // Si las listas tienen tamaños diferentes, no pueden ser iguales
        if (finalCosts1.size() != finalCosts2.size()) {
            return false;
        }

        Comparator<FinalCostEntity> comparator = Comparator
                .comparing(FinalCostEntity::getName)
                .thenComparing(FinalCostEntity::getType)
                .thenComparing(FinalCostEntity::getValue);

        finalCosts1.sort(comparator);
        finalCosts2.sort(comparator);

        // Comparar las listas elemento por elemento utilizando el Comparator
        for (int i = 0; i < finalCosts1.size(); i++) {
            FinalCostEntity cost1 = finalCosts1.get(i);
            FinalCostEntity cost2 = finalCosts2.get(i);

            // Si los objetos no son iguales según el Comparator, devolver false
            if (comparator.compare(cost1, cost2) != 0) {
                return false;
            }
        }
        return true;
    }


    public boolean tienenMismosInitialCosts(FacturaEntity factura1, FacturaEntity factura2) {
        List<InitialCostEntity> initialCosts1 = initialCostRepository.findByFactura_id(factura1.getId());
        List<InitialCostEntity> initialCosts2 = initialCostRepository.findByFactura_id(factura2.getId());

        if (initialCosts1.size() != initialCosts2.size()) {
            return false;
        }

        // Ordenar ambas listas con un Comparator explícito
        Comparator<InitialCostEntity> comparator = Comparator
                .comparing(InitialCostEntity::getName)
                .thenComparing(InitialCostEntity::getType)
                .thenComparing(InitialCostEntity::getValue);

        initialCosts1.sort(comparator);
        initialCosts2.sort(comparator);

        // Comparar las listas elemento por elemento usando el Comparator
        for (int i = 0; i < initialCosts1.size(); i++) {
            InitialCostEntity cost1 = initialCosts1.get(i);
            InitialCostEntity cost2 = initialCosts2.get(i);

            if (comparator.compare(cost1, cost2) != 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<FacturaSummaryDTO> findFacturasCartera(Integer facturaId) {
        FacturaEntity factura = getFacturaById(facturaId);
        List<FacturaEntity> list = facturaRepository.findFacturasCartera("ACEPTADO",
                factura.getDayByYear(),
                factura.getDiscountDate(),
                factura.getEffectiveRate(),
                factura.getRateTerm());

        list.removeIf(f -> f.getId().equals(facturaId));
        List<FacturaEntity> listValidada = new ArrayList<>();

        for (FacturaEntity facturaFound: list){
            if(tienenMismosInitialCosts(factura,facturaFound) && tienenMismosFinalCosts(factura,facturaFound)){
                listValidada.add(facturaFound);
            }
        }
        List<FacturaSummaryDTO> listSummary = new ArrayList<>();
        for(FacturaEntity fact : listValidada){
            FacturaSummaryDTO facturaSummaryDTO = new FacturaSummaryDTO(fact.getId(), fact.getState(),
                    factura.getOrder().getUser().getUsername(), fact.getStartDate(),fact.getDiscountDate(),fact.getTotalInvoiced(), fact.getNominalValue(),fact.getPaymentDate(), fact.getDays(),
                    fact.getRetention(),fact.getNewEffectiveRate(),fact.getDiscountedRate(), fact.getDiscount(),fact.getInitialCosts(),
                    fact.getFinalCosts(),fact.getNetWorth(),fact.getValueDelivered(),fact.getValueReceived(),fact.getTcea(),fact.getStartDate());
            listSummary.add(facturaSummaryDTO);
        }
        return listSummary;
    }
}