package com.Group_02.NovaGadgets_Api.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stores")
public class StoreEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "name is required")
    @NotNull(message = "name cannot be blank")
    @Size(min = 2, max = 40, message = "Name must be between 2 and 40 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "RUC is required")
    @NotNull(message = "RUC cannot be blank")
    @Pattern(regexp = "\\d{11}", message = "RUC must be exactly 11 digits")
    @Column(name = "ruc", nullable = false)
    private String ruc;
}
