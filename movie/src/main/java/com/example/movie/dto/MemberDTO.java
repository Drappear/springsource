package com.example.movie.dto;

import java.time.LocalDateTime;

import com.example.movie.entity.constant.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDTO {
    private Long mid;
    private String email;
    private String password;
    private String nickName;

    private MemberRole role;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
