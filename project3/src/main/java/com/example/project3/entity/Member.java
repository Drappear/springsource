package com.example.project3.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude = "team")
@Table(name = "team_member")
@Entity
public class Member {

    @Id
    private String id;

    private String memberName;

    // 관계
    // 주인이 누구인지 : @ManyToOne 설정한 entity가 주인
    @ManyToOne(cascade = CascadeType.ALL)
    private Team team; // 외래키
}
