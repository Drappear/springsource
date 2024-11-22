package com.example.board.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.ReplyDTO;
import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
@RestController
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/board/{bno}")
    public ResponseEntity<List<ReplyDTO>> getReplyList(@PathVariable Long bno) {
        log.info("{} 댓글 요청", bno);

        List<ReplyDTO> replies = replyService.list(bno);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }

    @PreAuthorize("authentication.name == #replyDto.replyerEmail")
    @PostMapping("/new")
    public ResponseEntity<Long> postReplyRegister(@RequestBody ReplyDTO replyDto) {
        log.info("댓글 작성 요청 {}", replyDto);

        Long rno = replyService.register(replyDto);
        return new ResponseEntity<Long>(rno, HttpStatus.OK);
    }

    @GetMapping("/{rno}")
    public ResponseEntity<ReplyDTO> getReplyRead(@PathVariable Long rno) {
        log.info("댓글 상세조회 {}", rno);
        ReplyDTO replyDto = replyService.read(rno);
        return new ResponseEntity<>(replyDto, HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<Long> putReply(@PathVariable Long rno, @RequestBody ReplyDTO replyDto) {
        log.info("댓글 수정 {}, {}", rno, replyDto);

        replyDto.setRno(rno);
        rno = replyService.modify(replyDto);

        return new ResponseEntity<Long>(rno, HttpStatus.OK);
    }

    @PreAuthorize("authentication.name == #replyDto.replyerEmail")
    @DeleteMapping("/{rno}")
    public ResponseEntity<Long> deleteReply(@PathVariable Long rno, @RequestBody ReplyDTO replyDto) {
        log.info("댓글 삭제 {}", rno);

        replyService.remove(rno);

        return new ResponseEntity<Long>(rno, HttpStatus.OK);
    }

}