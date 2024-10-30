package com.example.project2.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project2.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 20)
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
}
