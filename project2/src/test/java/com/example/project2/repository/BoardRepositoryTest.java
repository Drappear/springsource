package com.example.project2.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.project2.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 300)
                .forEach(i -> {
                    Board board = Board.builder()
                            .title("제목" + i)
                            .content("내용" + i)
                            .writer("작성자" + i)
                            .build();
                    boardRepository.save(board);
                });
    }

    @Test
    public void selectOneTest() {
        System.out.println(boardRepository.findById(5L).get());
    }

    @Test
    public void selectAllTest() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void updateTest() {
        Board board = boardRepository.findById(10L).get();
        board.setContent("내용 수정");
        boardRepository.save(board);
    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(15L);
    }

    // 쿼리 메소드
    @Test
    public void testTitleList() {
        // boardRepository.findByTitle("제목").forEach(b -> System.out.println(b));

        // boardRepository.findByTitleLike("제목").forEach(b -> System.out.println(b));

        // boardRepository.findByTitleStartingWith("제목1").forEach(b ->
        // System.out.println(b));

        // boardRepository.findByWriterEndingWith("작성자1").forEach(w ->
        // System.out.println(w));

        // boardRepository.findByWriterContaining("작성자1").forEach(w ->
        // System.out.println(w));

        boardRepository.findByWriterContainingOrTitleContaining("작성자9",
                "제목5").forEach(wt -> System.out.println(wt));

        // boardRepository.findByTitleContainingAndIdGreaterThan("제목", 15L).forEach(b ->
        // System.out.println(b));

        // boardRepository.findByIdGreaterThanOrderByIdDesc(10L).forEach(b ->
        // System.out.println(b));

        // 0 : 한 페이지 의미(두번째 페이지는 1), pageSize : 한 페이지에 보여질 게시물 개수
        // Pageable pageable = PageRequest.of(0, 10);

        // boardRepository.findByIdGreaterThanOrderByIdDesc(0L, pageable).forEach(b ->
        // System.out.println(b));

        // boardRepository.findByWriterList("작성자").forEach(b -> System.out.println(b));

        // boardRepository.findByTitle("제목1").forEach(b -> System.out.println(b));
    }
}
