package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Orders {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    // == 생성 메소드 == //
    public static Orders createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Orders orders = new Orders();
        orders.setMember(member);
        orders.setDelivery(delivery);
        Arrays.stream(orderItems).forEach(oi -> orders.addOrderItem(oi));
        orders.setOrderStatus(OrderStatus.주문완료);
        orders.setOrderDate(new Date());
        return orders;
    }

    // == 비즈니스 로직 == //
    /** 주문 취소 */
    public void cancel(){
        if(delivery.getDeliveryStatus() == DeliveryStatus.배달완료){
            throw new RuntimeException("이미 배달된 상품은 취소가 불가능합니다.");
        }
        this.setOrderStatus(OrderStatus.주문취소);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
    // == 조회 메소드 == //
    /** 전체 주문 가격 조회 */
    public int getTotalPrice(){
        int totalPrice = orderItems.stream()
                .mapToInt(oi -> oi.getTotalPrice())
                .reduce(Integer::sum).getAsInt();
        return totalPrice;
    }

    // == 편의 메소드 == //
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrders(this);
    }
}
