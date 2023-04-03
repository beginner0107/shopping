package com.group.shopping.domain.cart;

import com.group.shopping.domain.BaseEntity;
import com.group.shopping.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@ToString
@NoArgsConstructor
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    @Builder
    public Cart(Member member) {
        this.member = member;
    }
}
