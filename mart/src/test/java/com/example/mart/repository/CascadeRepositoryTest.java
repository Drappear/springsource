package com.example.mart.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.mart.entity.cascade.Child;
import com.example.mart.entity.cascade.Parent;
import com.example.mart.repository.cascade.ChildRepository;
import com.example.mart.repository.cascade.ParentRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class CascadeRepositoryTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Test
    public void insertTest() {
        Parent parent = Parent.builder()
                .name("부모5")
                .build();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            Child child = Child.builder()
                    .name("자식" + i)
                    .parent(parent)
                    .build();

            // parent2.getChilds().add(child); // child 정보는 db에 추가되지 않음

            childRepository.save(child);
        });
        // parentRepository.save(parent2);
    }

    @Test
    public void insertTest2() {
        Parent parent = Parent.builder()
                .name("부모5")
                .build();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            Child child = Child.builder()
                    .name("자식" + i)
                    .parent(parent)
                    .build();

            parent.getChilds().add(child);
        });
        parentRepository.save(parent);
    }

    @Test
    public void testChildRead() {
        // 자식2 정보 조회(+ 부모 조회)
        Child child2 = childRepository.findById(2L).get();
        System.out.println(child2);
        System.out.println(child2.getParent());
    }

    @Test
    public void testParentDelete() {
        // 부모 삭제 시 관련되어 있는 자식 같이 삭제
        // 자식 삭제 코드 작성하지 않고
        parentRepository.deleteById(1L);

    }

    @Commit
    @Transactional
    @Test
    public void testParentDelete2() {
        // 부모 삭제 시 관련되어 있는 자식 같이 삭제
        // 자식 삭제 코드 작성하지 않고
        Parent parent = parentRepository.findById(2L).get();

        parent.getChilds().remove(0);
        // parent.getChilds().remove(1);
        // parent.getChilds().remove(2);

        parentRepository.save(parent);

    }
}
