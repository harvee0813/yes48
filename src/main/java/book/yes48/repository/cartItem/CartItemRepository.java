package book.yes48.repository.cartItem;

import book.yes48.entity.cart.CartItem;
import book.yes48.web.form.cartItemDto.CartItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value = "select ci from CartItem ci where ci.myCart.id = :cartId")
    List<CartItemDto> findCartById(@Param("cartId") Long id);

    @Query(value = "select ci from CartItem ci where ci.goods.id = :goodsId and ci.myCart.id = :cartId")
    CartItem findCartItem(@Param("goodsId") String goodsId, @Param("cartId") String cartId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "delete from CartItem where goods.id = :goodsId and myCart.id = :findCartId")
    void deleteById(@Param("goodsId") String goodsId, @Param("findCartId") String findCartId);
}
