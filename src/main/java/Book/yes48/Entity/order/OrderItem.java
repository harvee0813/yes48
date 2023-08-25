package Book.yes48.Entity.order;

import Book.yes48.Entity.BaseTimeEntity;
import Book.yes48.Entity.goods.Goods;
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
