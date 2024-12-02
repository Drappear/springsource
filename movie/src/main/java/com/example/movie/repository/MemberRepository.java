package com.example.movie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    // 닉네임 수정
    @Modifying
    @Query("UPDATE Member m SET m.nickName=:nickName WHERE m.email=:email")
    void updateNickname(String nickName, String email);
}
