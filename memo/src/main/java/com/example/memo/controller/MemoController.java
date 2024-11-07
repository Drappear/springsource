package com.example.memo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/memo")
@Controller
public class MemoController {
    private final MemoService memoService;

    @GetMapping("/create")
    public void getCreateForm(MemoDTO memoDto) {
        log.info("메모 작성 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate(@Valid MemoDTO memoDto, BindingResult result, RedirectAttributes rttr) {
        log.info("메모 작성 : {}", memoDto);

        // 유효성 검증
        if (result.hasErrors()) {
            return "/memo/create";
        }

        Long mno = memoService.create(memoDto);
        rttr.addFlashAttribute("msg", mno + " 번 메모가 생성되었습니다.");
        return "redirect:list";
    }

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("메모 목록 요청");
        List<MemoDTO> memoDtoList = memoService.list();
        model.addAttribute("memoDtoList", memoDtoList);
    }

    @GetMapping(path = { "/read", "/update" })
    public void getRead(@RequestParam Long mno, Model model) {
        log.info("메모 조회 : {} ", mno);

        MemoDTO memoDto = memoService.read(mno);
        model.addAttribute("memoDto", memoDto);
    }

    @PostMapping("/update")
    public String postUpdate(MemoDTO memoDto, RedirectAttributes rttr) {
        log.info("수정 요청 : {}", memoDto);
        Long mno = memoService.update(memoDto);
        rttr.addFlashAttribute("msg", mno + " 번 메모가 수정되었습니다.");
        return "redirect:list";
    }

    @GetMapping("/remove")
    public String getRemove(@RequestParam Long mno, RedirectAttributes rttr) {
        log.info("메모 삭제 요청 : {}" + mno);
        memoService.delete(mno);
        rttr.addFlashAttribute("msg", mno + " 번 메모가 삭제되었습니다.");
        return "redirect:list";
    }

}
