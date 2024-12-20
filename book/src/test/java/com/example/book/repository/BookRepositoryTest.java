package com.example.book.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ScrollPosition.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void testCategoryList() {
        // 카테고리 목록
        categoryRepository.findAll().forEach(c -> System.out.println(c));

        // 출판사 목록
        publisherRepository.findAll().forEach(p -> System.out.println(p));
    }

    @Test
    public void testCategoryInsert() {
        // 소설, 건강, 컴퓨터, 여행, 경제
        categoryRepository.save(Category.builder().name("소설").build());
        categoryRepository.save(Category.builder().name("건강").build());
        categoryRepository.save(Category.builder().name("컴퓨터").build());
        categoryRepository.save(Category.builder().name("여행").build());
        categoryRepository.save(Category.builder().name("경제").build());
    }

    @Test
    public void testPublisherInsert() {
        // 미래의창, 웅진리빙하우스, 김영사, 길벗, 문학과지성사
        publisherRepository.save(Publisher.builder().name("미래의창").build());
        publisherRepository.save(Publisher.builder().name("웅진리빙하우스").build());
        publisherRepository.save(Publisher.builder().name("김영사").build());
        publisherRepository.save(Publisher.builder().name("길벗").build());
        publisherRepository.save(Publisher.builder().name("문학과지성사").build());
    }

    @Test
    public void testBookInsert() {
        IntStream.rangeClosed(16, 110).forEach(i -> {

            long num = (int) (Math.random() * 5) + 1;

            Book book = Book.builder()
                    .title("Book title " + i)
                    .writer("Writer" + i)
                    .price(10000 * i)
                    .salePrice((int) (15000 * i * 0.9))
                    .category(Category.builder().id(num).build())
                    .publisher(Publisher.builder().id(num).build())
                    .build();

            bookRepository.save(book);
        });
    }

    @Transactional
    @Test
    public void testList() {
        System.out.println(bookRepository.findAll());
    }

    @Test
    public void testGet() {
        // 특정 도서 조회
        Book book = bookRepository.findById(5L).get();
        System.out.println(book);
        System.out.println(book.getCategory().getName());
        System.out.println(book.getPublisher().getName());
    }

    // 페이지 나누기
    @Transactional
    @Test
    public void testPage() {
        // Pageable : spring boot에서 제공하는 페이지 처리 객체

        // 한페이지에 20개의 최신 도서정보
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").descending());
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate(null, null), pageable);

        System.out.println("total elements : " + result.getTotalElements());
        System.out.println("total pages : " + result.getTotalPages());
        result.getContent().forEach(book -> System.out.println(book));
    }

    @Test
    public void testPageSearch() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").descending());
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate("c", "건강"), pageable);

        System.out.println("total elements : " + result.getTotalElements());
        System.out.println("total pages : " + result.getTotalPages());
        result.getContent().forEach(book -> System.out.println(book));
    }
}
