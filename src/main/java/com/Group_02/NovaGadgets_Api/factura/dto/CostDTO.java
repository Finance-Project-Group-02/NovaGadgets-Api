package com.Group_02.NovaGadgets_Api.factura.dto;

import com.Group_02.NovaGadgets_Api.factura.model.FacturaEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CostDTO {
    private Integer id;
    private String name;
    private String type;
    private Double value;
}
