package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.service.MovieService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RequestMapping("/movie")
@Log4j2
@Controller
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/list")
    public void getMovieList(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO, Model model) {
        log.info("get 전체 영화 리스트 요청");

        PageResultDTO<MovieDTO, Object[]> result = movieService.getList(pageRequestDTO);

        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO, @RequestParam Long mno,
            Model model) {
        log.info("get 영화 상세정보 요청 {}", mno);
        MovieDTO movieDTO = movieService.getMovieDetail(mno);
        model.addAttribute("movieDTO", movieDTO);
    }

    @PostMapping("/modify")
    public String postModify(MovieDTO movieDTO, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("영화 정보 수정 {}", movieDTO);

        // 서비스
        Long mno = movieService.modify(movieDTO);

        rttr.addAttribute("mno", mno);
        rttr.addAttribute("page", 1);
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/movie/read";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long mno, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("영화 삭제 요청 {}", mno);

        movieService.delete(mno);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/movie/list";
    }

    @GetMapping("/create")
    public void getCreate(MovieDTO movieDTO, @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {
        log.info("get 영화 작성 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate(@Valid MovieDTO movieDTO, BindingResult result,
            @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("영화 등록 {}", movieDTO);

        if (result.hasErrors()) {
            return "/movie/create";
        }

        // 서비스
        Long mno = movieService.register(movieDTO);

        rttr.addAttribute("mno", mno);
        rttr.addAttribute("page", 1);
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/movie/read";
    }

}
