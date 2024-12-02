package com.example.movie.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.dto.AuthMemberDTO;
import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.entity.Member;
import com.example.movie.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class MemberDetailsServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username : {}", username);

        // 로그인 요청
        Optional<Member> result = memberRepository.findByEmail(username);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("이메일 확인");
        }

        // 이메일 존재시 entity => dto 변경
        Member member = result.get();

        MemberDTO memberDTO = MemberDTO.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .password(member.getPassword())
                .role(member.getRole())
                .build();

        return new AuthMemberDTO(memberDTO);
    }

    @Transactional
    @Override
    public void updateNickName(MemberDTO memberDTO) {
        memberRepository.updateNickname(memberDTO.getNickName(), memberDTO.getEmail());
    }

    @Transactional
    @Override
    public void updatePassword(PasswordDTO passwordDTO) throws Exception {
        // email을 이용해 사용자 찾기
        // Optional<Member> result =
        // memberRepository.findByEmail(passwordDTO.getEmail());
        // if (!result.isPresent()) {
        // throw new UsernameNotFoundException("이메일 확인");
        // }

        Member member = memberRepository.findByEmail(passwordDTO.getEmail()).get();

        // 현재 비밀번호(DB)가 입력 비밀번호(input)와 일치하는지 검증
        if (passwordEncoder.matches(passwordDTO.getCurrentPassword(), member.getPassword())) {
            // true: 비밀번호 수정
            member.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            memberRepository.save(member);
        } else {
            // false: 되돌리기
            throw new Exception("비밀번호를 확인해주세요");
        }

    }

}
