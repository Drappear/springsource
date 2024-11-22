package com.example.board.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.dto.MemberAuthDTO;
import com.example.board.dto.MemberDTO;
import com.example.board.entity.Member;
import com.example.board.entity.constant.MemberRole;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardUserDetailsService implements UserDetailsService, BoardUserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("login {}", username);

        // username으로 사용자 찾기
        Optional<Member> result = memberRepository.findById(username);
        // 없다면 UsernameNotFoundException
        if (!result.isPresent()) {
            throw new UsernameNotFoundException("이메일을 확인해주세요.");
        }
        // 있다면 MemberDTO + 인증값을 담아 리턴
        Member member = result.get();
        MemberDTO mDto = entityToDto(member);
        return new MemberAuthDTO(mDto);
    }

    @Override
    public String register(MemberDTO mDto) {
        // 중복 이메일 검사
        validateDuplicationMember(mDto.getEmail());

        // 비밀번호 암호화
        mDto.setPassword(passwordEncoder.encode(mDto.getPassword()));

        // 권한 부여
        mDto.setRole(MemberRole.MEMBER);

        // 저장
        Member member = memberRepository.save(dtoToEntity(mDto));

        // 이름 반환
        return member.getName();
    }

    // 중복 이메일 검사
    private void validateDuplicationMember(String email) throws IllegalStateException {
        Optional<Member> result = memberRepository.findById(email);

        if (result.isPresent()) {
            // 강제 exception 발생
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}
