package com.example.project2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project2.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // where title = ?
    List<Board> findByTitle(String title);

    // where title like ?
    List<Board> findByTitleLike(String title);
}
