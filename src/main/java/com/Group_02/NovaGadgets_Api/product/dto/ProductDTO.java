package com.Group_02.NovaGadgets_Api.product.dto;

import com.Group_02.NovaGadgets_Api.category.model.CategoryEntity;
import com.Group_02.NovaGadgets_Api.store.model.StoreEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Integer id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 40, message = "Name must be between 2 and 40 characters")
    private String name;

    @NotNull(message = "Image cannot be null")
    @NotBlank(message = "Image cannot be blank")
    @Size(min = 2, max = 100, message = "Image must be between 2 and 100 characters")
    private String image;

    @NotNull(message = "Details cannot be null")
    @NotBlank(message = "Details cannot be blank")
    @Size(min = 2, max = 100, message = "Details must be between 2 and 100 characters")
    private String details;

    @NotNull(message = "CategoryName cannot be null")
    @NotBlank(message = "CategoryName cannot be blank")
    private String categoryName;

    @Positive(message = "Price must be positive")
    private Integer quantity;

    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "StoreName cannot be null")
    @NotBlank(message = "StoreName cannot be blank")
    private String storeName;
}
