package com.example.guestbook.entity;

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
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@SequenceGenerator(name = "guest_book_seq_gen", sequenceName = "guest_book_seq", allocationSize = 1)
@Entity
public class GuestBook extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guest_book_seq_gen")
    @Id
    private Long gno;

    @Column(nullable = false, length = 50)
    private String writer;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 1500)
    private String content;

}
