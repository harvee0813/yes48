package book.yes48.entity.order;

import book.yes48.entity.BaseTimeEntity;
import book.yes48.entity.goods.Goods;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class OrderItem extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;
    private String state;
}
