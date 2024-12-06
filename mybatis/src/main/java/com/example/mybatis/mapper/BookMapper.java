package com.example.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.mybatis.dto.BookDTO;
import com.example.mybatis.dto.CategoryDTO;
import com.example.mybatis.dto.PageRequestDTO;
import com.example.mybatis.dto.PublisherDTO;

@Mapper
public interface BookMapper {
    public BookDTO selectBook(Long id);

    public List<BookDTO> selectBookList(PageRequestDTO pageRequestDTO);

    public int totalCnt(PageRequestDTO pageRequestDTO);

    public int update(BookDTO dto);

    public int delete(Long id);

    public List<PublisherDTO> publishers();

    public List<CategoryDTO> categories();

    public int create(BookDTO dto);
}
