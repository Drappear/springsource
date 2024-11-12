package com.example.project2.entity.jpql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@SequenceGenerator(name = "jpql_team_seq_gen", sequenceName = "jpql_team_seq", allocationSize = 1)
@Table(name = "jpql_team")
@Entity
public class Team {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpql_team_seq_gen")
    @Column(name = "team_id")
    @Id
    private Long id;

    @Column(name = "team_name")
    private String name;

}
