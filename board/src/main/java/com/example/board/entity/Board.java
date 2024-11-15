package com.example.board.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "writer", "replies" })
@Setter
@Getter
@SequenceGenerator(name = "board_seq_gen", sequenceName = "board_seq", allocationSize = 1)
@Entity
public class Board extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_gen")
    @Id
    private Long bno;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @Builder.Default
    @OneToMany(mappedBy = "board") // 기본 fetch 전략이 lazy
    private List<Reply> replies = new ArrayList<>();
}
