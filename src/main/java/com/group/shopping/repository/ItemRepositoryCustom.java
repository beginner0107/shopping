package com.group.shopping.repository;

import com.group.shopping.domain.item.Item;
import com.group.shopping.dto.item.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
