package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.movie.dto.AuthMemberDTO;
import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {
        log.info("로그인 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public void getProfile(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {
        log.info("프로필 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public void getEdit(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {
        log.info("프로필 수정 폼 요청");
    }

    // 닉네임 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/nickname")
    public String postEditNickName(MemberDTO memberDTO) {
        log.info("닉네임 변경 {}", memberDTO.getNickName());

        // 이메일 가져오기
        Authentication authentication = getAuthentication();
        AuthMemberDTO authMemberDTO = (AuthMemberDTO) authentication.getPrincipal();
        memberDTO.setEmail(authMemberDTO.getUsername());

        memberService.updateNickName(memberDTO);

        // SecurityContext에 보관된 값 업데이트
        authMemberDTO.getMemberDTO().setNickName(memberDTO.getNickName());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/member/profile";
    }

    // 비밀번호 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String postEditPassword(PasswordDTO passwordDTO, HttpSession session, RedirectAttributes rttr) {
        log.info("비밀번호 변경");

        // 서비스
        try {
            memberService.updatePassword(passwordDTO);
        } catch (Exception e) {
            // 실패 시 /edit
            e.printStackTrace();
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/edit";
        }
        // 성공 시 세션 해제 후 /login
        session.invalidate();
        return "redirect:/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/leave")
    public void getLeave(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {
        log.info("회원 탈퇴 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/leave")
    public String postLeave(PasswordDTO passwordDTO, boolean check, HttpSession session, RedirectAttributes rttr) {
        log.info("회원 탈퇴 요청 {},{}", passwordDTO, check);

        if (!check) {
            rttr.addFlashAttribute("error", "항목을 체크해주세요");
            return "/member/leave";
        }

        try {
            memberService.leave(passwordDTO);
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/leave";
        }

        session.invalidate();
        return "redirect:/movie/list";
    }

    // 회원가입
    @GetMapping("/register")
    public void getRegister(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO, MemberDTO memberDTO) {
        log.info("회원가입 폼 요청");
    }

    @PostMapping("/register")
    public String postRegister(@Valid MemberDTO memberDTO, BindingResult result, boolean check,
            @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO) {

        if (result.hasErrors()) {
            return "member/register";
        }

        memberService.register(memberDTO);

        return "redirect:/member/login";

    }

    // 개발자용 - Authentication 확인용
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @GetMapping("/auth")
    public Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }

}
