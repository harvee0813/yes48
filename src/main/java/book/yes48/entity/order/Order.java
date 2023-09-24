package book.yes48.entity.order;

import book.yes48.entity.member.Member;
import book.yes48.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderGoods> orderGoods = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private String state;

    private int totalPrice;
    private LocalDateTime orderDate;

    @Builder
    public Order(Member member, Delivery delivery, String state, int totalPrice) {
        this.member = member;
        this.delivery = delivery;
        this.state = state;
        this.totalPrice = totalPrice;
        this.orderDate = LocalDateTime.now();
    }
}
