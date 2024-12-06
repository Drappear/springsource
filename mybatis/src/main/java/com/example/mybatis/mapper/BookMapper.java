package com.example.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.mybatis.dto.BookDTO;
import com.example.mybatis.dto.PageRequestDTO;

@Mapper
public interface BookMapper {
    public BookDTO selectBook(Long id);

    public List<BookDTO> selectBookList(PageRequestDTO pageRequestDTO);

    public int totalCnt(PageRequestDTO pageRequestDTO);
}
