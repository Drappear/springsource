package com.example.guestbook.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestBookDTO {
    private long gno;
    private String writer;
    private String title;
    private String content;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
