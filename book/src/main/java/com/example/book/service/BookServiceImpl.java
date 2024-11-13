package com.example.book.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.dto.CategoryDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.dto.PublisherDTO;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import com.example.book.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public Long create(BookDTO dto) {
        return bookRepository.save(dtoToEntity(dto)).getId();
    }

    @Override
    public BookDTO getRow(Long id) {
        return entityToDto(bookRepository.findById(id).get());
    }

    @Override
    public PageResultDTO<BookDTO, Book> getList(PageRequestDTO pageRequestDTO) {
        // 페이지 나누기 x
        // List<Book> result = bookRepository.findAll();
        // return result.stream().map(entity ->
        // entityToDto(entity)).collect(Collectors.toList());

        // 페이지 나누기 o
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        Page<Book> result = bookRepository
                .findAll(bookRepository.makePredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword()), pageable);

        Function<Book, BookDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public Long update(BookDTO dto) {
        Book book = bookRepository.findById(dto.getId()).get();
        book.setPrice(dto.getPrice());
        book.setSalePrice(dto.getSalePrice());
        return bookRepository.save(book).getId();
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> getCategoryList() {
        List<Category> result = categoryRepository.findAll();

        return result.stream().map(c -> entityToDto(c)).collect(Collectors.toList());
    }

    @Override
    public List<PublisherDTO> getPublisherList() {
        List<Publisher> result = publisherRepository.findAll();

        return result.stream().map(p -> entityToDto(p)).collect(Collectors.toList());
    }
}
