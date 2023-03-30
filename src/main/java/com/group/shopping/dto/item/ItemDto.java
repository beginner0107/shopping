package com.group.shopping.dto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ItemDto {

    private Long id;
    private String itemNm;
    private Integer price;
    private String itemDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @Builder
    public ItemDto(Long id,
                   String itemNm,
                   Integer price,
                   String itemDetail,
                   String sellStatCd,
                   LocalDateTime regTime,
                   LocalDateTime updateTime) {
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
        this.itemDetail = itemDetail;
        this.sellStatCd = sellStatCd;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }
}
