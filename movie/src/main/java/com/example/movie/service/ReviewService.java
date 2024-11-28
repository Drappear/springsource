package com.example.movie.service;

import java.util.List;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewService {

    // movie 번호 이용 특정영화의 모든 리뷰 조회
    List<ReviewDTO> getReviews(Long mno);

    // 특정 리뷰 조회
    ReviewDTO getReview(Long reviewNo);

    Long addReview(ReviewDTO reviewDTO);

    Long modifyReview(ReviewDTO reviewDTO);

    void removeReview(Long reviewNo);

    default ReviewDTO entityDto(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewNo(review.getReviewNo())
                .grade(review.getGrade())
                .text(review.getText())
                .mno(review.getMovie().getMno())
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .nickName(review.getMember().getNickName())
                .regDate(review.getRegDate())
                .updateDate(review.getUpdateDate())
                .build();

        return reviewDTO;
    }

    default Review dtoToEntity(ReviewDTO reviewDTO) {
        return Review.builder()
                .reviewNo(reviewDTO.getReviewNo())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .member(Member.builder().mid(reviewDTO.getMid()).build())
                .movie(Movie.builder().mno(reviewDTO.getMno()).build())
                .build();
    }
}