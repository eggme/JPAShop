package com.example.demo.services;

import com.example.demo.domain.*;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberRepository memberRepository;

    /** 전체 주문 조회 */
    public List<Orders> findAll(){
        return orderRepository.findAll();
    }

    /** 주문 */
    public Long order(Long memberId, Long itemId, int count){

        // 엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemService.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery(member.getAddress());
        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 주문 생성
        Orders orders = Orders.createOrder(member, delivery, orderItem);

        Orders saveOrders = orderRepository.save(orders);
        return saveOrders.getId();
    }

    public Orders findOrder(Long orderId){
        return orderRepository.findById(orderId).get();
    }

    /** 주문 취소 */
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Orders orders = orderRepository.findById(orderId).get();
        orders.cancel();
    }

    /** 주문 검색 미구현 */
//    public List<Orders> findOrderList(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
