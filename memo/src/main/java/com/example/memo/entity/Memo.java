package com.example.memo.entity;

import groovy.transform.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@SequenceGenerator(name = "memo_seq_gen", sequenceName = "memo_seq", allocationSize = 1)
@Entity
public class Memo extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memo_seq_gen")
    @Id
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
