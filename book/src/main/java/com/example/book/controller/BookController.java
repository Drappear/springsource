package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.example.book.dto.BookDTO;
import com.example.book.dto.CategoryDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.dto.PublisherDTO;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/book")
@Controller
public class BookController {

    private final BookService bookService;

    // 목록
    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list page request");

        PageResultDTO<BookDTO, Book> result = bookService.getList(pageRequestDTO);

        model.addAttribute("result", result);
    }

    // 상세조회
    @GetMapping(value = { "/read", "/modify" })

    public void getRead(@RequestParam Long id, Model model) {
        log.info("read {} book request", id);

        BookDTO dto = bookService.getRow(id);

        model.addAttribute("dto", dto);

    }

    // 수정
    @PostMapping("/modify")
    public String postModify(BookDTO dto, RedirectAttributes rttr) {
        log.info("post modify {} book request", dto);

        Long id = bookService.update(dto);

        // 상세조회로 이동
        rttr.addAttribute("id", id);
        return "redirect:read";
    }

    // 삭제
    @PostMapping("/remove")
    public String postRemove(@RequestParam Long id) {
        log.info("post remove {} book request", id);

        bookService.delete(id);

        return "redirect:list";
    }

    // 추가
    @GetMapping("/create")
    public void getcreate(@ModelAttribute("dto") BookDTO dto, Model model) {
        log.info("get book create form request");

        List<CategoryDTO> categories = bookService.getCategoryList();
        List<PublisherDTO> publishers = bookService.getPublisherList();

        model.addAttribute("cats", categories);
        model.addAttribute("pubs", publishers);
    }

    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("dto") BookDTO dto, BindingResult result, Model model,
            RedirectAttributes rttr) {
        log.info("post book create request");

        List<CategoryDTO> categories = bookService.getCategoryList();
        List<PublisherDTO> publishers = bookService.getPublisherList();

        model.addAttribute("cats", categories);
        model.addAttribute("pubs", publishers);

        if (result.hasErrors()) {
            return "/book/create";
        }

        Long id = bookService.create(dto);
        // rttr.addAttribute("id", id);
        // return "redirect:read";
        rttr.addFlashAttribute("msg", id + " 번 도서가 등록되었습니다.");
        return "redirect:list";
    }

}
