package com.example.book.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.CategoryDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.dto.PublisherDTO;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequestMapping("/rest")
@RequiredArgsConstructor
@Log4j2
@RestController
public class BookRestController {

    private final BookService bookService;

    // 목록
    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<BookDTO, Book>> getList(PageRequestDTO pageRequestDTO) {
        log.info("get list page 요청");

        PageResultDTO<BookDTO, Book> result = bookService.getList(pageRequestDTO);

        return new ResponseEntity<PageResultDTO<BookDTO, Book>>(result, HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<String> postCreate(BookDTO dto) {
        log.info("post book create 요청");

        Long id = bookService.create(dto);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> postModify(@PathVariable Long id, BookDTO dto) {
        log.info("post modify book 요청");

        dto.setId(id);
        id = bookService.update(dto);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> postRemove(@PathVariable Long id) {
        log.info("post remove {} book 요청", id);

        bookService.delete(id);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}
