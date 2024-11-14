package com.example.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.guestbook.dto.GuestBookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.service.GuestBookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/guestbook")
@Controller
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO, Model model) {
        log.info("get list page");
        log.info("requestDto {} ", pageRequestDTO);

        PageResultDTO<GuestBookDTO, GuestBook> result = service.list(pageRequestDTO);

        model.addAttribute("result", result);
    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRead(@RequestParam Long gno, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            Model model) {
        GuestBookDTO dto = service.read(gno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(GuestBookDTO dto, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        Long gno = service.update(dto);

        rttr.addAttribute("gno", gno);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:read";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long gno, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        service.delete(gno);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:list";
    }

    @GetMapping("/register")
    public void getRegister(@ModelAttribute("dto") GuestBookDTO dto) {
        log.info("guestbook 작성 폼 요청");
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("dto") GuestBookDTO dto, BindingResult result,
            RedirectAttributes rttr) {
        log.info("guestbook 등록 요청");
        if (result.hasErrors()) {
            return "/guestbook/register";
        }

        Long gno = service.register(dto);

        rttr.addFlashAttribute("msg", gno);
        rttr.addAttribute("page", 1);
        rttr.addAttribute("size", 10);
        rttr.addAttribute("type", "");
        rttr.addAttribute("keyword", "");

        return "redirect:list";
    }

}
