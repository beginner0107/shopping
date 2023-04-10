package com.group.shopping.repository;

import com.group.shopping.domain.item.Item;
import com.group.shopping.dto.item.ItemSearchDto;
import com.group.shopping.dto.main.MainItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
