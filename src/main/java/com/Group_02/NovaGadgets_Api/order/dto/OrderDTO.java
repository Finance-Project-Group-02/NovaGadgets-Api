package com.Group_02.NovaGadgets_Api.order.dto;

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
public class OrderDTO {

    @Positive(message = "idUser must be positive")
    @NotNull(message = "idUser cannot be null")
    private Integer idUser;

    @NotNull(message = "orderDate cannot be null")
    private LocalDate orderDate;

    @Positive(message = "TotalInvoiced must be positive")
    @NotNull(message = "TotalInvoiced cannot be null")
    private Double totalInvoiced;
}
