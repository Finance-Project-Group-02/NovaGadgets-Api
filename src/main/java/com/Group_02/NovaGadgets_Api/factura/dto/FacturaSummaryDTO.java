package com.Group_02.NovaGadgets_Api.factura.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaSummaryDTO {
    private Integer id;
    private String state;
    private String username;
    private LocalDate orderDate;
    private Double totalInvoiced;
}
