package com.example.club.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.club.entity.ClubMember;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
    // WHERE club_member_email=? and club_member_from_social = ?
    @EntityGraph(attributePaths = { "roles" }, type = EntityGraphType.LOAD)
    Optional<ClubMember> findByEmailAndFromSocial(String email, boolean fromSocial);
}

// @EntityGraph
// ㄴ fetch 속성이 LAZY인 경우 특정 기능에서만 EAGER로 동작하도록 설정
// --- attributePaths 에 표시한 속성만 EAGER로 처리
