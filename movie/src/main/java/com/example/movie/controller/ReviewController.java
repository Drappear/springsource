package com.example.movie.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/reviews")
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public List<ReviewDTO> getReviewList(@PathVariable Long mno) {
        log.info("get 리뷰 리스트 요청 {}", mno);

        List<ReviewDTO> reviews = reviewService.getReviews(mno);

        return reviews;
    }

    @PreAuthorize("authentication.name == #email")
    @DeleteMapping("/{mno}/{reviewNo}")
    public Long deleteReview(@PathVariable Long reviewNo, String email) {
        log.info("리뷰 삭제 {}", reviewNo);

        reviewService.removeReview(reviewNo);
        return reviewNo;
    }

    @GetMapping("/{mno}/{reviewNo}")
    public ReviewDTO getReviewRow(@PathVariable Long reviewNo) {
        log.info("리뷰 조회 {}", reviewNo);

        return reviewService.getReview(reviewNo);
    }

    @PreAuthorize("authentication.name == #reviewDTO.email")
    @PutMapping("/{mno}/{reviewNo}")
    public Long putReviewModify(@PathVariable Long reviewNo, @RequestBody ReviewDTO rDto) {
        log.info("리뷰 수정 {}, {}", reviewNo, rDto);
        rDto.setReviewNo(reviewNo);
        return reviewService.modifyReview(rDto);
    }

    @PostMapping("/{mno}")
    public Long postReview(@RequestBody ReviewDTO rDto) {
        log.info("리뷰 등록 {}", rDto);
        return reviewService.addReview(rDto);
    }

}
