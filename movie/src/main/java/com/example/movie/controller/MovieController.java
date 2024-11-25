package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.service.MovieService;

@RequiredArgsConstructor
@RequestMapping("/movie")
@Log4j2
@Controller
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/list")
    public void getMovieList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("get 전체 영화 리스트 요청");

        PageResultDTO<MovieDTO, Object[]> result = movieService.getList(pageRequestDTO);

        model.addAttribute("result", result);
    }

}
