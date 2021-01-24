package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    private Orders order;

    private int orderPrice;
    private int count;

    // == 생성 메소드 == //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStork(count);
        return orderItem;
    }

    // ==비즈니스 로직== //
    /** 주문 취소 */
    public void cancel() {
        getItem().addStock(count);
    }
    // == 조회 로직 == //
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }
}
