package com.example.project2.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.project2.entity.Member;
import com.example.project2.entity.jpql.Address;
import com.example.project2.entity.jpql.JpqlMember;
import com.example.project2.entity.jpql.Order;
import com.example.project2.entity.jpql.Product;
import com.example.project2.entity.jpql.QJpqlMember;
import com.example.project2.entity.jpql.QProduct;
import com.example.project2.entity.jpql.Team;
import com.example.project2.repository.jpql.JpqlMemberRepository;
import com.example.project2.repository.jpql.OrderRepository;
import com.example.project2.repository.jpql.ProductRepository;
import com.example.project2.repository.jpql.TeamRepository;
import com.querydsl.core.BooleanBuilder;

@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private JpqlMemberRepository jpqlMemberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Team team = Team.builder().name("team" + i).build();
            teamRepository.save(team);

            JpqlMember member = JpqlMember.builder().name("name" + i).age(i).team(team).build();
            jpqlMemberRepository.save(member);

            Product product = Product.builder().name("product" + i).price(i * 1000).stockAmount(i + 10).build();
            productRepository.save(product);
        });
    }

    @Test
    public void insertOrderTest() {

        Address address = new Address();
        address.setCity("서울");
        address.setStreet("110-10");
        address.setZipcode("11011");

        // 2번 member가 3번 product 주문
        LongStream.rangeClosed(1, 3).forEach(l -> {

            Order order = Order.builder()
                    .address(address)
                    .orderAmount(15)
                    .member(JpqlMember.builder().id(2L).build())
                    .product(Product.builder().id(l).build())
                    .build();

            orderRepository.save(order);
        });
    }

    @Test
    public void testFindAll() {
        // jpqlMemberRepository.findMembers().forEach(m -> System.out.println(m));
        // System.out.println(jpqlMemberRepository.findMembers());

        // System.out.println(jpqlMemberRepository.findMembers(Sort.by("age")));
        // System.out.println(jpqlMemberRepository.findMembers(Sort.by(Sort.Direction.DESC,
        // "age")));

        List<Object[]> list = jpqlMemberRepository.findMembers2();

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
            System.out.printf("name = %s, age = %d\n", objects[0], objects[1]);
        }
    }

    @Test
    public void testAddress() {
        System.out.println(orderRepository.findByAddress());
    }

    @Test
    public void testOrders() {
        List<Object[]> result = orderRepository.findByOrders();

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void testQueryMethod() {
        // System.out.println(jpqlMemberRepository.findByName("member1"));
        // System.out.println(jpqlMemberRepository.findByAgeGreaterThan(5));
        // System.out.println(jpqlMemberRepository.findByTeam(Team.builder().id(3L).build()));

        // List<Object[]> result = jpqlMemberRepository.aggregate();
        // for (Object[] objects : result) {
        // System.out.println(Arrays.toString(objects));
        // System.out.println("인원수 : " + objects[0]);
        // System.out.println("합계 : " + objects[1]);
        // System.out.println("평균 : " + objects[2]);
        // System.out.println("연장자 : " + objects[3]);
        // System.out.println("최연소 : " + objects[4]);
        // }

        // System.out.println(jpqlMemberRepository.findByTeam2("team2"));

        // List<Object[]> result = jpqlMemberRepository.findByTeam3("team3");
        // List<Object[]> result = jpqlMemberRepository.findByTeam4("team4");
        List<Object[]> result = jpqlMemberRepository.findByTeam5("team5");
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
            System.out.println("member : " + objects[0]);
            System.out.println("team : " + objects[1]);
        }
    }

    // QueryDSL 테스트
    @Test
    public void queryDslTest() {
        QProduct qProduct = QProduct.product;
        QJpqlMember qJpqlMember = QJpqlMember.jpqlMember;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 상품명이 product1인 상품 조회
        // Iterable<Product> products =
        // productRepository.findAll(qProduct.name.eq("product1"));

        // 상품명이 product1이고 가격이 500 초과인 상품 조회
        // Iterable<Product> products =
        // productRepository.findAll(qProduct.name.eq("product1").and(qProduct.price.gt(500)));

        // 상품명이 product1이고 가격이 500 초과인 상품 조회(BooleanBuilder)
        booleanBuilder.and(qProduct.name.eq("product1")).and(qProduct.price.gt(500));
        Iterable<Product> products = productRepository.findAll(booleanBuilder);
        for (Product product : products) {
            System.out.println(product);
        }

        // 상품명이 product1이고 가격이 500 이상인 상품 조회
        // Iterable<Product> products = productRepository.findAll(
        // qProduct.name.eq("product1").and(qProduct.price.goe(500)));

        // 상품명에 'product' 글자가 들어있는 상품 조회
        // Iterable<Product> products = productRepository.findAll(
        // qProduct.name.contains("product"));

        // 상품명이 'product' 로 시작하는 상품 조회
        // Iterable<Product> products = productRepository.findAll(
        // qProduct.name.startsWith("product"));

        // 상품명이 '3'으로 끝나는 상품 조회
        // Iterable<Product> products = productRepository.findAll(
        // qProduct.name.endsWith("3"));
        // for (Product product : products) {
        // System.out.println(product);
        // }

        // member name 이 name3인 회원 조회
        // Iterable<JpqlMember> members =
        // jpqlMemberRepository.findAll(qJpqlMember.name.eq("name3"));

        // member name 이 name3인 회원 조회(id 기준 내림차순 정렬)
        // booleanBuilder.and(qJpqlMember.name.eq("name3"));
        // Iterable<JpqlMember> members = jpqlMemberRepository.findAll(booleanBuilder,
        // Sort.by("id").descending());
        // for (JpqlMember jpqlMember : members) {
        // System.out.println(jpqlMember);
        // }
    }
}
