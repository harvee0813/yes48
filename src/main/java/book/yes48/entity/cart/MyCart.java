package book.yes48.entity.cart;

import book.yes48.entity.BaseTimeEntity;
import book.yes48.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "my_cart")
public class MyCart extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "myCart", cascade = CascadeType.REMOVE)
    private List<CartItem> cartItems;

    @Builder
    public MyCart(Member member, List<CartItem> cartItems) {
        this.member = member;
        this.cartItems = cartItems;
    }

    public void setCartItems(CartItem cartItem) {
        this.cartItems = cartItems;
    }
}
