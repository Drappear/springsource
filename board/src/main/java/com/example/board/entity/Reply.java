package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = { "board", "replyer" })
@Setter
@Getter
@SequenceGenerator(name = "board_reply_seq_gen", sequenceName = "board_reply_seq", allocationSize = 1)
@Entity
public class Reply extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_reply_seq_gen")
    @Id
    private Long rno;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
