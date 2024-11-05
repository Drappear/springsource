package com.example.mart.entity.item;

import com.example.mart.entity.constant.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "order")
@Setter
@Getter
@SequenceGenerator(name = "mart_delivery_seq_gen", sequenceName = "mart_delivery_seq", allocationSize = 1)
@Table(name = "mart_delivery")
@Entity
public class Delivery extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_delivery_seq_gen")
    @Column(name = "mart_delivery_id")
    @Id
    private Long id;

    @Column(name = "mart_delivery_address", nullable = false)
    private String addr;

    @Column(name = "mart_delivery_address_detail", nullable = false)
    private String addrDetail;

    @Column(name = "mart_delivery_postal", nullable = false)
    private String postal;

    @Column(name = "mart_delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
