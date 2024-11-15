package com.Group_02.NovaGadgets_Api.factura.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaSummaryDTO {
    private Integer id;
    private String state;
    private String username;
    private LocalDate orderDate;
    private LocalDate discountDate;
    private Double totalInvoiced;
    private Double nominalValue;
    private LocalDate paymentDate;
    private Integer days;
    private Double retention;
    private Double newEffectiveRate;
    private Double discountedRate;
    private Double discount;
    private Double initialCosts;
    private Double finalCosts;
    private Double netWorth;
    private Double valueDelivered;
    private Double valueReceived;
    private Double tcea;
}
