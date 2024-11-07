package com.example.memo.repository;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.memo.entity.Memo;

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void testMemoInsert() {
        LongStream.rangeClosed(1, 10).forEach(l -> {
            Memo memo = Memo.builder()
                    .memoText("메모 내용" + l)
                    .build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testMemoRead() {
        // 특정 메모 (6번)
        Memo memo = memoRepository.findById(6L).get();
        System.out.println(memo);
        // 전체 메모
        List<Memo> memoList = memoRepository.findAll();
        memoList.forEach(m -> {
            System.out.println(m);
        });
    }

    @Test
    public void testMemoUpdate() {
        // 7번 메모 내용 수정
        Memo memo = memoRepository.findById(7L).get();
        memo.setMemoText("수정된 메모 내용");
        memoRepository.save(memo);
    }

    @Test
    public void testMemoDelete() {
        memoRepository.deleteById(10L);
    }
}
