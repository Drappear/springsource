package com.example.movie.service;

import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;

public interface MemberService {

    // 닉네임 수정
    void updateNickName(MemberDTO memberDTO);

    // 비밀번호 수정
    void updatePassword(PasswordDTO passwordDTO) throws Exception;
    // 회원가입

    // 회원탈퇴
}
