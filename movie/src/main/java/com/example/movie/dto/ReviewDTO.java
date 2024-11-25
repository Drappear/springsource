package com.example.movie.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewDTO {
    private Long reviewNo;
    private int grade;
    private String text;

    // Movie의 mno
    private Long mno;

    // Member mid, nickName, email
    private Long mid;
    private String nickName;
    private String email;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
