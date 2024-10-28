package com.example.project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project1.dto.LoginDto;
import com.example.project1.dto.MemberDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public void getLogin(LoginDto loginDto) {
        log.info("login 페이지 요청");
    }

    // @PostMapping("/login")
    // public void postLogin(HttpServletRequest request) {
    // log.info("login 요청 - 사용자 입력값 요청");
    // String userId = request.getParameter("userId");
    // String password = request.getParameter("password");

    // log.info("user id : " + userId);
    // log.info("password : " + password);
    // }

    // @PostMapping("/login")
    // public void postLogin(String userId, String password) {
    // log.info("login 요청 - 사용자 입력값 요청");

    // log.info("user id : " + userId);
    // log.info("password : " + password);
    // }

    @PostMapping("/login")
    public String postLogin(@Valid LoginDto loginDto, BindingResult result) {
        log.info("login 요청 - 사용자 입력값 요청");

        log.info("user id : " + loginDto.getUserId());
        log.info("password : " + loginDto.getPassword());

        if (result.hasErrors()) {
            return "/member/login";
        }

        return "/member/login";
    }

    @GetMapping("/register")
    public void getRegister(MemberDto memberDto) {
        log.info("register 요청");
    }

    @PostMapping("/register")
    public String postRegister(@Valid MemberDto memberDto, BindingResult result) {
        log.info("회원가입 요청");

        if (result.hasErrors()) {
            return "/member/register";
        }

        return "redirect:/member/login"; // redirect : 경로
    }

    // http://localhost:8080/path1 + get
    @GetMapping("/path1")
    public String method1() {
        return "login"; // login.html
    }

    // http://localhost:8080/path2 + post
    @PostMapping("/path2") // /path2.html
    public void method2() {

    }

    @GetMapping("/path3")
    public String method3() {
        return "redirect:/login"; // http://localhost:8080/login
    }

}
