package com.group.shopping.dto.item;

import com.group.shopping.domain.constant.ItemSellStatus;
import com.group.shopping.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    public ItemFormDto(Item item) {
        this.id = item.getId();
        this.itemNm = item.getItemNm();
        this.price = item.getPrice();
        this.itemDetail = item.getItemDetail();
        this.stockNumber = item.getStockNumber();
        this.itemSellStatus = item.getItemSellStatus();
    }

    public Item createItem() {
        return Item.builder()
                .id(id)
                .itemNm(itemNm)
                .price(price)
                .stockNumber(stockNumber)
                .itemDetail(itemDetail)
                .itemSellStatus(itemSellStatus)
                .build();
    }

    public static ItemFormDto of(Item item) {
        return new ItemFormDto(item);
    }
}
