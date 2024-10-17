package com.Group_02.NovaGadgets_Api.factura.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaResponseDTO {
    private LocalDate startDate;
    private Double totalInvoiced;
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
