package com.example.demo.services;

import com.example.demo.domain.*;
import com.example.demo.exception.NotEnoughStockException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("상품주문")
    public void orderItem(){
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Orders getOrder = orderService.findOrder(orderId);

        System.out.println("주문 상품 개수 = " + getOrder.getOrderItems().get(0).getCount());
        System.out.println("주문 상품 종류의 수 = " +getOrder.getOrderItems().size());
        System.out.println("주문 상태 = " + getOrder.getOrderStatus());
        System.out.println("총 주문 가격 = " + getOrder.getTotalPrice());
        System.out.println("남은 수량 = " + item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    @DisplayName("상품주문 재고수량 초과")
    public void notEnoughItemStock() throws Exception{
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 11;

        orderService.order(member.getId(), item.getId(), orderCount);

        fail("상품주문 재고수량 초과입니다.");
    }

    @Test
    @DisplayName("주문 취소")
    @Transactional
    public void cancelOrder(){
        Member member = createMember();
        Item item = createBook("사골 육수 JPA", 1000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Orders getOrder = orderService.findOrder(orderId);

        System.out.println("주문 상태 = " + getOrder.getOrderStatus());
        System.out.println("상품 재고 = " + item.getStockQuantity());
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("전현준");
        member.setAddress(new Address("광주", "북문대로 201번길 30", "61048"));
        memberService.joinMember(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        itemService.saveItem(book);
        return book;
    }

}