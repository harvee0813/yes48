package book.yes48.entity.order;

import book.yes48.entity.BaseTimeEntity;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //주문

    private int price;
    private int quantity;
    private String state;

    @Builder
    public OrderGoods(Goods goods, Member member, int price, int quantity) {
        this.goods = goods;
        this.member = member;
        this.price = price;
        this.quantity = quantity;
        this.state = "WAIT";
    }

    public void updateState(String state) {
        this.state = state;
    }

    public void updateOrder(Order order) {
        this.order = order;
    }
}
