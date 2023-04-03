package com.group.shopping.domain.order;

import com.group.shopping.domain.constant.ItemSellStatus;
import com.group.shopping.domain.item.Item;
import com.group.shopping.domain.member.Member;
import com.group.shopping.repository.ItemRepository;
import com.group.shopping.repository.MemberRepository;
import com.group.shopping.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem() {
        return Item.builder()
                .itemNm("테스트 상품")
                .price(10000)
                .itemDetail("상세설명")
                .itemSellStatus(ItemSellStatus.SELL)
                .stockNumber(100)
                .build();
    }

    @DisplayName("영속성 전이 테스트")
    @Test
    void cascadeTest() {
        Order order = new Order();

        for (int i = 0; i < 3; i ++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .count(10)
                    .orderPrice(1000)
                    .order(order)
                    .build();
            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityExistsException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    public Order createOrder() {
        Order order = new Order(); // 하나의 주문

        for (int i = 0; i < 3; i ++) {
            Item item = createItem(); // 3 개의 상품 저장
            itemRepository.save(item); // 쿼리 + 3
            OrderItem orderItem = OrderItem.builder() // 주문 내역에 대해 저장할 엔티티
                    .item(item)
                    .count(10)
                    .orderPrice(1000)
                    .order(order)
                    .build();
            order.getOrderItems().add(orderItem); // 영속성 전이
        }

        Member member = new Member();
        memberRepository.save(member); // 쿼리 +1

        order.saveMember(member);
        orderRepository.save(order); // 쿼리 + 4 -> 총 8개의 insert 쿼리 확인(O)
        return order;
    }

    @DisplayName("고아객체 제거 테스트")
    @Test
    void orphanRemovalTest() {
        Order order = this.createOrder();
        order.getOrderItems().remove(0); // 부모 엔티티와 연관관계가 끊어졌기 때문에 삭제 쿼리문 실행
        em.flush();
    }
}
