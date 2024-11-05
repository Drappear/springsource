package com.example.mart.repository;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entity.constant.DeliveryStatus;
import com.example.mart.entity.constant.OrderStatus;
import com.example.mart.entity.item.Delivery;
import com.example.mart.entity.item.Item;
import com.example.mart.entity.item.Member;
import com.example.mart.entity.item.Order;
import com.example.mart.entity.item.OrderItem;
import com.example.mart.repository.item.DeliveryRepository;
import com.example.mart.repository.item.ItemRepository;
import com.example.mart.repository.item.MemberRepository;
import com.example.mart.repository.item.OrderItemRepository;
import com.example.mart.repository.item.OrderRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    // C
    @Test
    public void memberInsert() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .name("회원" + i)
                    .postal("100-" + i)
                    .addr("서울시")
                    .addrDetail("10" + i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void itemInsert() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Item item = Item.builder()
                    .name("상품" + i)
                    .price(10000 * i)
                    .amount(10 * i)
                    .build();
            itemRepository.save(item);
        });
    }

    @Test
    public void orderInsertTest() {

        Member member = memberRepository.findById(1L).get();
        Item item = itemRepository.findById(5L).get();

        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.ORDER)
                .member(member)
                .build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .price(150000)
                .amount(3)
                .order(order)
                .item(item)
                .build();
        orderItemRepository.save(orderItem);
    }

    // R

    @Test
    public void memberAndItemAndOrderListTest() {
        // 주문 내역 조회
        // orderRepository.findAll().forEach(order -> System.out.println(order));
        // Order(id=1, orderDate=2024-11-04T13:06:43.103301, status=ORDER)
        // Order(id=2, orderDate=2024-11-04T13:07:08.503832, status=ORDER)
        // Order(id=3, orderDate=2024-11-04T13:07:29.101789, status=ORDER)

        // 주문 상품 상세 조회
        orderItemRepository.findAll().forEach(orderItem -> {
            System.out.println(orderItem);
            // 상품 상세
            System.out.println(orderItem.getItem());
            // 주문 상세 내역
            System.out.println(orderItem.getOrder());
            // 주문자 상세
            System.out.println(orderItem.getOrder().getMember());
        });
    }

    @Test
    public void memberAndItemAndOrderRowTest() {

        OrderItem orderItem = orderItemRepository.findById(1L).get();
        // 주문 상품 상세 조회
        System.out.println(orderItem);
        // 상품 상세
        System.out.println(orderItem.getItem());
        // 주문 상세 내역
        System.out.println(orderItem.getOrder());
        // 주문자 상세
        System.out.println(orderItem.getOrder().getMember());
    }

    // U

    @Test
    public void memberAndItemAndOrderUpdateTest() {

        // 주문자의 주소 변경
        // Member member = Member.builder()
        // .id(1L)
        // .name("회원1")
        // .postal("100-100")
        // .addr("부산시")
        // .addrDetail("1111")
        // .build();
        Member member = memberRepository.findById(1L).get();
        member.setAddr("부산시");

        // save : insert or update
        // entity매니저가 있어 현재 entity가 new 인지 기존 entity인지 구분 가능
        // new : insert 호출 / 기존 entity : update 호출
        // update는 무조건 전체 컬럼이 수정되는 형태로 작성됨

        memberRepository.save(member);

        // 1번 주문상품 변경

        // 상품 수량, 가격 변경
        Item item = itemRepository.findById(1L).get();
        item.setAmount(20);
        item.setPrice(25000);
        itemRepository.save(item);

        OrderItem orderItem = orderItemRepository.findById(1L).get();
        orderItem.setPrice(125000);
        orderItemRepository.save(orderItem);
    }

    // D
    @Test
    public void memberAndItemAndOrderDeleteTest() {
        // 주문 상품 취소
        orderItemRepository.deleteById(1L);
        // 주문 취소
        orderRepository.deleteById(1L);
    }

    // 양방향
    // Order ==> OrderItem 객체 그래프 탐색
    @Transactional
    @Test
    public void testOrderItemList() {
        Order order = orderRepository.findById(2L).get();
        System.out.println(order);
        order.getOrderItemsList().forEach(orderItem -> System.out.println(orderItem));
    }

    @Transactional
    @Test
    public void testOrderList() {
        Member member = memberRepository.findById(1L).get();
        System.out.println(member);

        member.getOrderList().forEach(order -> System.out.println(order));
    }

    @Test
    public void testDeliveryInsert() {
        // 배송 정보 입력
        Delivery delivery = Delivery.builder()
                .postal("배송지우편번호")
                .addr("배송지 주소")
                .addrDetail("배송지 상세주소")
                .deliveryStatus(DeliveryStatus.READY)
                .build();

        deliveryRepository.save(delivery);

        // order와 배송정보 연결
        Order order = orderRepository.findById(2L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Test
    public void testOrderRead() {
        // order 조회(+ 배송정보)
        Order order = orderRepository.findById(2L).get();
        System.out.println(order);
        System.out.println(order.getDelivery());
    }

    // 양방향(배송 => 주문)
    @Test
    public void testDeliveryRead() {
        // 배송정보 조회(+ order)
        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println(delivery);
        System.out.println(delivery.getOrder());
    }
}
