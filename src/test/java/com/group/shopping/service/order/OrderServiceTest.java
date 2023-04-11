package com.group.shopping.service.order;

import static org.junit.jupiter.api.Assertions.*;

import com.group.shopping.domain.constant.ItemSellStatus;
import com.group.shopping.domain.item.Item;
import com.group.shopping.domain.member.Member;
import com.group.shopping.domain.order.Order;
import com.group.shopping.domain.order.OrderItem;
import com.group.shopping.dto.order.OrderDto;
import com.group.shopping.repository.ItemRepository;
import com.group.shopping.repository.MemberRepository;
import com.group.shopping.repository.OrderRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem() {
        Item item = Item.builder()
            .itemNm("테스트 상품")
            .price(10000)
            .itemDetail("테스트 상품 상세 설명")
            .itemSellStatus(ItemSellStatus.SELL)
            .stockNumber(100)
            .build();
        return itemRepository.save(item);
    }

    public Member saveMember () {
        Member member = Member.builder()
            .email("test@test.com")
            .build();
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문 테스트")
    public void order() {
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto, member.getEmail());

        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount() * item.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());
    }
}