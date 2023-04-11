package com.group.shopping.service.order;

import com.group.shopping.domain.item.Item;
import com.group.shopping.domain.member.Member;
import com.group.shopping.domain.order.Order;
import com.group.shopping.domain.order.OrderItem;
import com.group.shopping.dto.order.OrderDto;
import com.group.shopping.repository.ItemRepository;
import com.group.shopping.repository.MemberRepository;
import com.group.shopping.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId())
                                  .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email)
                                        .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);
        return order.getId();
    }

}
