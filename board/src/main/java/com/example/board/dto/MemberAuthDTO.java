package com.example.board.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// @Builder
@ToString
@Setter
@Getter
public class MemberAuthDTO extends User {

    private MemberDTO memberDTO;

    // 리스트 생성방법
    // 1.
    // List<String> list = new ArrayList<>();
    // list.add();
    // 2.
    // private List<String> list = List.of("spring", "java", "sdk");
    // 3.
    // List<String> list = Arrays.asList("spring", "java", "sdk");

    public MemberAuthDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MemberAuthDTO(MemberDTO memberDTO) {
        this(memberDTO.getEmail(), memberDTO.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDTO.getRole().toString())));
        this.memberDTO = memberDTO;
    }
}
