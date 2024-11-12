package com.example.book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.entity.Book;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService service;

    @Transactional
    @Test
    public void testList() {
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(3)
                .size(10)
                .build();

        PageResultDTO<BookDTO, Book> resultDto = service.getList(requestDTO);
        resultDto.getDtoList().forEach(dto -> System.out.println(dto));
        System.out.println("요청 페이지" + resultDto.getPage());
        System.out.println("목록 개수" + resultDto.getSize());
        System.out.println("시작 페이지" + resultDto.getStart());
        System.out.println("마지막 페이지" + resultDto.getEnd());
        System.out.println("페이지 목록" + resultDto.getPageList());
        System.out.println("다음 페이지 여부" + resultDto.isNext());
        System.out.println("이전 페이지 여부" + resultDto.isPrev());
        System.out.println("전체 페이지" + resultDto.getTotalPage());
    }
}
