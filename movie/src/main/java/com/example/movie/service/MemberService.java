package com.example.movie.service;

import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.constant.MemberRole;

public interface MemberService {

    // 닉네임 수정
    void updateNickName(MemberDTO memberDTO);

    // 비밀번호 수정
    void updatePassword(PasswordDTO passwordDTO) throws Exception;

    // 회원가입
    String register(MemberDTO memberDTO);

    // 회원탈퇴
    void leave(PasswordDTO passwordDTO) throws Exception;

    default Member dtoToEntity(MemberDTO memberDTO) {
        return Member.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .nickName(memberDTO.getNickName())
                // .mid(memberDTO.getMid())
                .role(MemberRole.MEMBER)
                .build();
    }

    // default MemberDTO entityToDto(Member member) {
    // return MemberDTO.builder()
    // .email(member.getEmail())
    // .password(member.getPassword())
    // .nickName(member.getNickName())
    // .mid(member.getMid())
    // .role(member.getRole())
    // .build();
    // }
}
