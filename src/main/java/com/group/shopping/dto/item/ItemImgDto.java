package com.group.shopping.dto.item;

import com.group.shopping.domain.item.ItemImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String reqImgYn;

    public ItemImgDto(ItemImg itemImg) {
        this.id = itemImg.getId();
        this.imgName = itemImg.getImgName();
        this.oriImgName = itemImg.getOriImgName();
        this.imgUrl = itemImg.getImgUrl();
        this.reqImgYn = itemImg.getRepimgYn();
    }

    public static ItemImgDto of(ItemImg itemImg) {
        return new ItemImgDto(itemImg);
    }
}
