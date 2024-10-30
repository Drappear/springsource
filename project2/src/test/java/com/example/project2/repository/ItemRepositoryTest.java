package com.example.project2.repository;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project2.entity.Item;
import com.example.project2.entity.constant.ItemStatus;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    Item item = Item.builder()
                            .itemNm("운동화" + i)
                            .price(95000)
                            .stockNumber(15)
                            .itemSellStatus(ItemStatus.SELL)
                            .regTime(LocalDateTime.now())
                            .build();
                    itemRepository.save(item);
                });
    }

    @Test
    public void selectOneTest() {
        System.out.println(itemRepository.findById(5L).get());
    }

    @Test
    public void selectAllTest() {
        itemRepository.findAll().forEach(item -> System.out.println(item));
    }

    @Test
    public void updateTest() {
        // id가 6인 운동화
        Item item = itemRepository.findById(6L).get();
        // 가격
        item.setPrice(120000);
        // 업데이트 시간
        item.setUpdateTime(LocalDateTime.now());
        // 업데이트
        itemRepository.save(item);
    }

    @Test
    public void deleteTest() {
        // id 9인 운동화 삭제
        itemRepository.deleteById(9L);
    }
}
