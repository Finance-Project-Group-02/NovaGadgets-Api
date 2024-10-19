package com.Group_02.NovaGadgets_Api.factura.model;

import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;
import com.Group_02.NovaGadgets_Api.user.model.UsersEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facturas")
public class FacturaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "state", nullable = false)
    private String state;
    //Datos de Entrada
    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;
    @Column(name = "payment_date", nullable = true)
    private LocalDate paymentDate;
    @Column(name = "discount_date", nullable = true)
    private LocalDate discountDate;
    @Column(name = "retention", nullable = true)
    private Double retention;
    @Column(name = "effective_rate", nullable = true)
    private Double effectiveRate;
    @Column(name = "rate_term", nullable = true)
    private Integer rateTerm;
    @Column(name = "day_by_year", nullable = true)
    private Character dayByYear;
    @Column(name = "total_invoiced", nullable = false)
    private Double totalInvoiced;
    @Column(name = "initial_costs", nullable = true)
    private Double initialCosts;
    @Column(name = "final_costs", nullable = true)
    private Double finalCosts;

    //Datos de Salida (null temporal)
    @Column(name = "days", nullable = true)
    private Integer days;
    @Column(name = "new_effective_rate", nullable = true)
    private Double newEffectiveRate;
    @Column(name = "discounted_rate", nullable = true)
    private Double discountedRate;
    @Column(name = "discount", nullable = true)
    private Double discount;
    @Column(name = "net_worth", nullable = true)
    private Double netWorth;
    @Column(name = "value_delivered", nullable = true)
    private Double valueDelivered;
    @Column(name = "value_received", nullable = true)
    private Double valueReceived;
    @Column(name = "tcea", nullable = true)
    private Double tcea;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
}
