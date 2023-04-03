package com.group.shopping.domain.item;

import com.group.shopping.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "item_img")
@Getter
public class ItemImg extends BaseEntity {

    @Id @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String imgName; // 이미지 파일명
    
    private String oriImgName; // 원본 이미지 파일명
    
    private String imgUrl; // 이미지 조회 경로
    
    private String repimgYn; // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    public void saveItem(Item item) {
        this.item = item;
    }

    public void changeRepimg(String representative) {
        this.repimgYn = representative;
    }
}
