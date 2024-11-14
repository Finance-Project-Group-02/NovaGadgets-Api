package com.Group_02.NovaGadgets_Api.orderDetail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenDetailRequestDTO {
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @Positive(message = "ProductId must be positive")
    private Integer productId;

    @Positive(message = "Price must be positive")
    private Double productPrice;
}
