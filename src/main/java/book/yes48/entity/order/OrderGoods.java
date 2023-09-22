package book.yes48.entity.order;

import book.yes48.entity.BaseTimeEntity;
import book.yes48.entity.cart.CartItem;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class OrderGoods extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int price;
    private int quantity;

    @Builder
    public OrderGoods(Goods goods, Member member, int price, int quantity) {
        this.goods = goods;
        this.member = member;
        this.price = price;
        this.quantity = quantity;
    }
}
