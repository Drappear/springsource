package com.example.mybatis.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.example.mybatis.dto.BookDTO;
import com.example.mybatis.dto.CategoryDTO;
import com.example.mybatis.dto.PageRequestDTO;
import com.example.mybatis.dto.PageResultDTO;
import com.example.mybatis.dto.PublisherDTO;
import com.example.mybatis.service.BookService;

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
    public void getList(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO, Model model) {
        log.info("도서 전체 목록 요청 {}", pageRequestDTO);

        List<BookDTO> result = bookService.getList(pageRequestDTO);
        int total = bookService.totalCnt(pageRequestDTO);

        log.info("list {}", result);
        log.info("total {}", total);

        model.addAttribute("result", new PageResultDTO<>(pageRequestDTO, total, result));
    }

    // 상세조회
    @GetMapping(value = { "/read", "/modify" })

    public void getRead(@RequestParam Long id,
            @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO, Model model) {
        log.info("read {} book request", id);
        log.info("requestDto {} ", pageRequestDTO);

        BookDTO dto = bookService.getRow(id);

        model.addAttribute("dto", dto);

    }

    // 수정
    @PostMapping("/modify")
    public String postModify(BookDTO dto, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("post modify {} book request", dto);
        log.info("requestDto {} ", pageRequestDTO);

        if (bookService.update(dto)) {

            // 상세조회로 이동
            rttr.addAttribute("id", dto.getId());
            rttr.addAttribute("page", pageRequestDTO.getPage());
            rttr.addAttribute("size", pageRequestDTO.getSize());
            rttr.addAttribute("type", pageRequestDTO.getType());
            rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

            return "redirect:read";
        } else {
            return "/book/modify";
        }
    }

    // 삭제
    @PostMapping("/remove")
    public String postRemove(@RequestParam Long id, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("post remove {} book request", id);
        log.info("requestDto {} ", pageRequestDTO);

        bookService.delete(id);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

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
            RedirectAttributes rttr, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {
        log.info("post book create request");

        List<CategoryDTO> categories = bookService.getCategoryList();
        List<PublisherDTO> publishers = bookService.getPublisherList();

        model.addAttribute("cats", categories);
        model.addAttribute("pubs", publishers);

        if (result.hasErrors()) {
            return "/book/create";
        }

        bookService.create(dto);

        rttr.addFlashAttribute("msg", "도서가 등록되었습니다.");
        rttr.addAttribute("page", 1);
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:list";
    }

}
