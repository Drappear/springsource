package com.example.movie.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.entity.Member;
import com.example.movie.entity.constant.MemberRole;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void memberInsertTest() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Member member = Member.builder()
                    .email("member" + i + "@email.com")
                    .password(passwordEncoder.encode("1111"))
                    .nickName("memNick" + i)
                    .role(MemberRole.MEMBER)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void updateTest() {
        Member member = memberRepository.findById(1L).get();
        member.setNickName("new");
        memberRepository.save(member);
    }

    @Transactional
    @Test
    public void updateTest2() {

        memberRepository.updateNickname("testNick", "member1@email.com");

    }

    @Commit
    @Transactional
    @Test
    public void deleteTest() {
        reviewRepository.deleteByMember(Member.builder().mid(23L).build());
        memberRepository.deleteById(23L);
    }
}
