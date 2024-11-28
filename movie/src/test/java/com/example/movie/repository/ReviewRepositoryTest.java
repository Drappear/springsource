package com.example.movie.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MovieRepository movieRepository;

    @Test
    public void reviewInsertTest() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            int num = (int) (Math.random() * 50) + 1;
            int num2 = (int) (Math.random() * 5) + 1;

            Review review = Review.builder()
                    .grade(num2)
                    .text("movie review by member" + num)
                    .member(memberRepository.findById((long) num).get())
                    .movie(movieRepository.findById((long) num).get())
                    .build();

            reviewRepository.save(review);
        });
    }

    @Transactional
    @Test
    public void getTest() {
        Movie movie = movieRepository.findById(21L).get();
        List<Review> list = reviewRepository.finByMovie(movie);
        // System.out.println(list);
        list.forEach(review -> {
            System.out.println(review.getText());
            System.out.println(review.getGrade());
            System.out.println(review.getMember().getNickName());
        });
    }
}
