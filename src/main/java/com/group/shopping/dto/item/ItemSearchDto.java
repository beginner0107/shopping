package com.group.shopping.dto.item;

import com.group.shopping.domain.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ItemSearchDto {

    private String searchDateType; // 현재 시간과 상품 등록일을 비교해서 상품 데이터를 조회

    private ItemSellStatus searchSellStatus; // 상품의 판매상태를 기준으로 상품 데이터를 조회

    private String searchBy; // 상품을 조회할 때 어떤 유형으로 조회할지 선택

    private String searchQuery = "";
}
