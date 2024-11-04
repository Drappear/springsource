package com.example.project3.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project3.entity.Member;
import com.example.project3.entity.Team;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamMemberRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void createTeamTest() {
        Team teamEntity = Team.builder().id("team1").name("팀1").build();
        teamRepository.save(teamEntity);
        teamEntity = Team.builder().id("team2").name("팀2").build();
        teamRepository.save(teamEntity);
    }

    @Test
    public void createMemberTest() {
        Team team1 = teamRepository.findById("team1").get();
        Team team2 = Team.builder().id("team2").build();

        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .id("user" + i)
                    .memberName("MEM" + i)
                    .team(team2)
                    .build();
            memberRepository.save(member);
        });

        IntStream.rangeClosed(6, 10).forEach(i -> {
            Member member = Member.builder()
                    .id("user" + i)
                    .memberName("MEM" + i)
                    .team(team1)
                    .build();
            memberRepository.save(member);
        });

    }

    @Test
    public void selectTest() {
        // 회원 찾기
        Member memberEntity = memberRepository.findById("user1").get();
        System.out.println(memberEntity);

        // 팀 정보 찾기
        System.out.println(memberEntity.getTeam());
        // 팀명
        System.out.println(memberEntity.getTeam().getName());
    }

    @Test
    public void memberEqualTeamTest() {
        memberRepository.findByMemberEqualTeam("팀1").forEach(m -> System.out.println(m));
    }

    @Test
    public void updateTest() {
        Member memberEntity = memberRepository.findById("user6").get();

        Team team2 = teamRepository.findById("team2").get();

        memberEntity.setTeam(team2);
        memberRepository.save(memberEntity);
    }

    @Test
    public void deleteTest() {

        // 외래키 제약조건에서는 자식부터 삭제
        // 자식의 팀 변경 또는 삭제
        Team team = Team.builder().id("team1").build();
        List<Member> members = memberRepository.findByTeam(team);
        // members.forEach(member -> System.out.println(member));

        Team team2 = teamRepository.findById("team2").get();

        members.forEach(member -> {
            member.setTeam(team2);
            memberRepository.save(member);
        });

        // team1 제거
        teamRepository.deleteById("team1");

    }

    @Test
    public void memberAndTeamInsertTest() {
        // EntityNotFoundException : cascade 없을 경우
        Team team = Team.builder().id("team3").name("팀3").build();
        Member member = Member.builder().id("user11").memberName("홍길동").team(team).build();
        memberRepository.save(member);
    }

    @Test
    public void memberAndTeamUpdateTest() {
        Team team = teamRepository.findById("team3").get();
        team.setName("victory");

        Member member = Member.builder().id("user11").memberName("홍길동").team(team).build();
        memberRepository.save(member);
    }

    @Transactional
    @Test
    public void selectMemberTest() {
        // 팀 찾기
        Team team2 = teamRepository.findById("team2").get();

        team2.getMembers().forEach(t -> {
            // 팀 정보 출력
            System.out.println(t);

            // 팀에 속한 member의 이름 출력
            System.out.println(t.getMemberName());
        });
    }

    @Test
    public void teamAndMemberInsertTest() {
        Team team = Team.builder().id("team3").name("팀3").build();
        Member member = Member.builder().id("user12").memberName("성춘향").team(team).build();
        team.getMembers().add(member);
        teamRepository.save(team);

        // 원래 방식
        // Team team = Team.builder().id("team4").name("팀4").build();
        // teamRepository.save(team);
        // Member member =
        // Member.builder().id("user12").memberName("성춘향").team(team).build();
        // memberRepository.save(member);
    }
}
