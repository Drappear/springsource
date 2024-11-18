package com.example.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RequestMapping("/rest")
@Log4j2
@RestController // 화면단은 어떤것이 들어와도 상관없음
public class MemoRestController {

    private final MemoService memoService;

    @GetMapping("/list")
    public List<MemoDTO> getList() {
        log.info("메모 목록 요청");
        List<MemoDTO> list = memoService.list();

        return list;
    }

    @GetMapping("/{mno}")
    public MemoDTO getRead(@PathVariable Long mno) {
        log.info("메모 조회 {} ", mno);

        MemoDTO dto = memoService.read(mno);
        return dto;
    }

    @PostMapping("/create")
    public ResponseEntity<String> postCreate(@RequestBody MemoDTO memoDto) {
        log.info("메모 작성 : {}", memoDto);

        Long mno = memoService.create(memoDto);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    // rest 추가되는 method : PUT(or patch) / DELETE
    @PutMapping("/{mno}")
    public ResponseEntity<String> postUpdate(@PathVariable Long mno, @RequestBody MemoDTO memoDto) {
        log.info("수정 요청 : {}", memoDto);
        memoDto.setMno(mno);
        memoService.update(memoDto);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/{mno}")
    @GetMapping("/remove")
    public ResponseEntity<String> getRemove(@PathVariable Long mno) {
        log.info("메모 삭제 요청 : {}" + mno);
        memoService.delete(mno);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

}
