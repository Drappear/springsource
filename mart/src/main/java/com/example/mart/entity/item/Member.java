package com.example.mart.entity.item;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = "orderList")
@Setter
@Getter
@SequenceGenerator(name = "mart_member_seq_gen", sequenceName = "mart_member_seq", allocationSize = 1)
@Table(name = "mart_member")
@Entity
public class Member extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_member_seq_gen")
    @Column(name = "member_id")
    @Id
    private Long id;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_postal")
    private String postal;

    @Column(name = "member_addr")
    private String addr;

    @Column(name = "member_addr_detail")
    private String addrDetail;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();
}
