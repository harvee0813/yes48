package book.yes48.web.form.cartItemDto;

import book.yes48.entity.cart.CartItem;
import book.yes48.entity.cart.MyCart;
import book.yes48.entity.goods.Goods;
import lombok.Getter;

/**
 * 조회된 cartItem을 담는 폼
 */
@Getter
public class CartItemDto {

    private Long id;
    private MyCart myCart;
    private Goods goods;
    private int quantity;

    public CartItemDto(Long id, MyCart myCart, Goods goods, int quantity) {
        this.id = id;
        this.myCart = myCart;
        this.goods = goods;
        this.quantity = quantity;
    }

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.myCart = cartItem.getMyCart();
        this.goods = cartItem.getGoods();
        this.quantity = cartItem.getQuantity();
    }
}
