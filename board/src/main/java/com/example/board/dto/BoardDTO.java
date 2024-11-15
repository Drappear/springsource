package com.example.board.dto;

import java.time.LocalDateTime;

import com.example.board.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
    private Long bno;

    private String title;

    private String content;

    // private Member member;
    private String writerName;
    private String writerEmail;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    // 댓글 개수
    private Long replyCnt;

}
