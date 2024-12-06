package com.example.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@Setter
@Getter
public class PageRequestDTO {
    private int page;
    private int size;

    // 검색
    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public String[] getTypeArr() {
        return type == null ? new String[] {} : type.split("");
    }

}
