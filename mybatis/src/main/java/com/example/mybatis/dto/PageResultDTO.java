package com.example.mybatis.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Data;

@Data
public class PageResultDTO<DTO, EN> {

    // 총 개수
    private int total;
    private PageRequestDTO requestDTO;

    // 총 페이지 수
    private int totalPage;

    // 시작 페이지, 끝 페이지 번호
    private int start, end;

    // 이전, 다음 여부
    private boolean prev, next;

    // 화면에 보여줄 페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(PageRequestDTO requestDTO, int total) {
        this.total = total;

        int tempEnd = (int) (Math.ceil(requestDTO.getPage() / 10.0)) * requestDTO.getSize();
        totalPage = (int) (Math.ceil((total / 1.0) / requestDTO.getSize()));

        this.start = tempEnd - 9;
        this.prev = this.start > 1;
        this.end = totalPage > tempEnd ? tempEnd : totalPage;
        this.next = totalPage > tempEnd;

        // IntStream.rangeClosed(start, end) : int

        pageList = IntStream.rangeClosed(start, end)
                .boxed() // int ==> Integer
                .collect(Collectors.toList());
    }

}