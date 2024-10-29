package com.example.project2.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.project2.entity.constant.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 회원 테이블
// id, userName, age
// 회원가입일, 수정일
// 회원 - 관리자, 회원으로 구분됨
// 회원 이름은 필수, 10자 이내

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "membertbl")
public class Member {
    @Id
    private String id;

    @Column(name = "name", nullable = false, length = 30)
    private String userName;

    private int age;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private RoleType roleType;
}
