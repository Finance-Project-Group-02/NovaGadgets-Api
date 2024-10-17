package com.Group_02.NovaGadgets_Api.factura.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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
public class FacturaRequestDTO {

    @NotNull(message = "State cannot be null")
    @NotBlank(message = "State cannot be blank")
    private String state;

    @NotNull(message = "startDate cannot be null")
    private LocalDate startDate;

    @NotNull(message = "PaymentDate cannot be null")
    private LocalDate paymentDate;

    @NotNull(message = "DiscountDate cannot be null")
    private LocalDate discountDate;

    @Positive(message = "Retention must be positive")
    @NotNull(message = "Retention cannot be null")
    private Double retention;

    @Positive(message = "EffectiveRate must be positive")
    @NotNull(message = "EffectiveRate cannot be null")
    private Double effectiveRate;

    @Positive(message = "RateTerm must be positive")
    @NotNull(message = "RateTerm cannot be null")
    private Integer rateTerm;

    @NotNull(message = "DayByYear cannot be null")
    private Character dayByYear;

    @Positive(message = "TotalInvoiced must be positive")
    @NotNull(message = "TotalInvoiced cannot be null")
    private Double totalInvoiced;

    @NotNull(message = "InitialCosts cannot be null")
    private List<Double> initialCosts;

    @NotNull(message = "FinalCosts cannot be null")
    private List<Double> finalCosts;
}
