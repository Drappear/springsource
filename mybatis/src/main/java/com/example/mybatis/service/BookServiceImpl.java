package com.example.mybatis.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.mybatis.dto.BookDTO;
import com.example.mybatis.dto.CategoryDTO;
import com.example.mybatis.dto.PageRequestDTO;
import com.example.mybatis.dto.PageResultDTO;
import com.example.mybatis.dto.PublisherDTO;
import com.example.mybatis.mapper.BookMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    @Override
    public Long create(BookDTO dto) {
        return null;
    }

    @Override
    public BookDTO getRow(Long id) {
        return null;
    }

    @Override
    public Long update(BookDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<CategoryDTO> getCategoryList() {
        return null;
    }

    @Override
    public List<PublisherDTO> getPublisherList() {
        return null;
    }

    @Override
    public List<BookDTO> getList(PageRequestDTO pageRequestDTO) {
        return bookMapper.selectBookList(pageRequestDTO);
    }

    @Override
    public int totalCnt(PageRequestDTO pageRequestDTO) {
        return bookMapper.totalCnt(pageRequestDTO);
    }
}