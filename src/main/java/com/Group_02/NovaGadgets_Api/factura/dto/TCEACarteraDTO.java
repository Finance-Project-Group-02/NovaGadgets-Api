package com.Group_02.NovaGadgets_Api.factura.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TCEACarteraDTO {
    private Double totalValueReceived;
    private Double tceaCartera;
}
