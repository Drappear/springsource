package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;

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
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO, Model model) {
        log.info("get, list 페이지 요청");
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam Long bno, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            Model model) {
        log.info("get, 상세 조회 요청");
        BoardDTO dto = boardService.read(bno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(BoardDTO dto, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {

        Long bno = boardService.update(dto);

        rttr.addAttribute("bno", bno);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:read";
    }

    @PostMapping("/remove")
    public String postRemove(Long bno, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("post remove 요청 {}", bno);
        boardService.remove(bno);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:list";
    }

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("dto") BoardDTO dto,
            @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {
        log.info("get create 등록 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("dto") BoardDTO dto, BindingResult result,
            @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("post create 등록 요청");

        if (result.hasErrors()) {
            return "/board/create";
        }
        Long bno = boardService.register(dto);

        rttr.addAttribute("bno", bno);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:read";
    }

}
