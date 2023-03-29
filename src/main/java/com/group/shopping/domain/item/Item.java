package com.group.shopping.domain.item;

import com.group.shopping.domain.item.constant.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Entity
@Table(name = "item")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명
    
    @Column(name = "price", nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime regTime; // 등록 시간

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateTime;

    @Builder
    public Item(Long id,
                String itemNm,
                int price,
                int stockNumber,
                String itemDetail,
                ItemSellStatus itemSellStatus,
                LocalDateTime regTime,
                LocalDateTime updateTime) {
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }
}