package com.example.movie.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MovieDTO {
    private Long mno;
    private String title;

    // 영화에 소속된 이미지 가져오기
    @Builder.Default
    private List<MovieImageDTO> movieImageDTOs = new ArrayList<>();

    // 영화 평점 평균
    private double reviewAvg;
    // 영화 평가 수
    private Long reviewCnt;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

}