package com.group.shopping.service.cart;

import static org.junit.jupiter.api.Assertions.*;

import com.group.shopping.domain.cart.CartItem;
import com.group.shopping.domain.constant.ItemSellStatus;
import com.group.shopping.domain.item.Item;
import com.group.shopping.domain.member.Member;
import com.group.shopping.dto.cart.CartItemDto;
import com.group.shopping.repository.CartItemRepository;
import com.group.shopping.repository.ItemRepository;
import com.group.shopping.repository.MemberRepository;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

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

    public Member saveMember() {
        Member member = Member.builder().email("test@test.com").build();
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    void addCart() {
        Item item = saveItem();
        Member member = saveMember();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                                              .orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(), cartItem.getItem().getId());
        assertEquals(cartItemDto.getCount(), cartItem.getCount());
    }
}