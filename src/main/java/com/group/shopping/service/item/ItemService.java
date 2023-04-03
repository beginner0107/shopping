package com.group.shopping.service.item;

import com.group.shopping.domain.item.Item;
import com.group.shopping.domain.item.ItemImg;
import com.group.shopping.dto.item.ItemFormDto;
import com.group.shopping.repository.ItemImgRepository;
import com.group.shopping.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 이미지 파일이 비어 있으면 DB에 저장해줄 필요가 없음
        itemImgFileList.removeIf(multipartFile -> Objects.equals(multipartFile.getOriginalFilename(), ""));

        //이미지 등록
        int imgCount = itemImgFileList.size();
        for (int i = 0; i < imgCount; i ++) {
            ItemImg itemImg = new ItemImg();
            itemImg.saveItem(item);
            if (i == 0) {
                itemImg.changeRepimg("Y");
            } else {
                itemImg.changeRepimg("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }
}
