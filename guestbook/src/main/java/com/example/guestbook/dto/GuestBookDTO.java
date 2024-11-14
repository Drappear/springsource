package com.example.guestbook.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotBlank(message = "writer는 필수 입력 요소입니다.")
    private String writer;

    @NotBlank(message = "title은 필수 입력 요소입니다.")
    private String title;

    @NotEmpty(message = "content는 필수 입력 요소입니다.")
    private String content;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
