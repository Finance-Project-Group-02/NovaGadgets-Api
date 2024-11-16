package com.Group_02.NovaGadgets_Api.factura.model;

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
@Entity
@Table(name = "final_cost")
public class FinalCostEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    private String type;
    private Double value;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    private FacturaEntity factura;
}
