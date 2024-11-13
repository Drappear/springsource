package com.example.guestbook;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.example.guestbook.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest
public class GuestBookRepositoryTest {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void testGuestBookInsert() {
        IntStream.rangeClosed(1, 300).forEach(i -> {

            GuestBook guestBook = GuestBook.builder()
                    .writer("guest" + i)
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .build();

            guestBookRepository.save(guestBook);
        });
    }

    @Test
    public void testGuestBookUpdate() {
        GuestBook guestBook = guestBookRepository.findById(300L).get();
        guestBook.setTitle("수정된 제목");
        guestBook.setContent("수정된 내용");
        guestBookRepository.save(guestBook);
    }

    @Test
    public void testGuestBookRead() {
        QGuestBook qGuestBook = QGuestBook.guestBook;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        String keyword = "";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expressionTitle = qGuestBook.title.contains(keyword);
        BooleanExpression expressionContent = qGuestBook.content.contains(keyword);
        builder.and(expressionTitle.or(expressionContent));
        builder.and(qGuestBook.gno.gt(0L));

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        result.stream().forEach(guestBook -> System.out.println(guestBook));
    }

    @Test
    public void testGuestBookSearch() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        Page<GuestBook> result = guestBookRepository.findAll(guestBookRepository.makePredicate("tc", "Title"),
                pageable);

        result.stream().forEach(guestBook -> System.out.println(guestBook));
    }

    @Test
    public void testGuestBookDelete() {
        guestBookRepository.deleteById(250L);
    }

}
