package com.example.movie.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.movie.dto.PageRequestDTO;

@Log4j2
@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome() {
        return "redirect:/movie/list";
    }

    @GetMapping("/access-denied")
    public String getError(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {
        // 403
        return "/except/denied";
    }

    @GetMapping("/error")
    public String get404(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {
        // 컨트롤러에 없는 경로 요청시
        return "/except/url404";
    }

}
