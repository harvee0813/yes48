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

    /**
     * cartId로 cartItem에 담긴 전체 상품 조회
     * @param id 장바구니 아이디
     * @return
     */
    @Query(value = "select ci from CartItem ci where ci.myCart.id = :cartId")
    List<CartItemDto> findCartById(@Param("cartId") Long id);

    /**
     * goodsId와 cartId로 cartItem 조회
     * @param goodsId 상품 아이디
     * @param cartId 장바구니 아이디
     * @return
     */
    @Query(value = "select ci from CartItem ci where ci.goods.id = :goodsId and ci.myCart.id = :cartId")
    CartItem findCartItem(@Param("goodsId") String goodsId, @Param("cartId") String cartId);

    /**
     * goodsId와 cartId로 cartItem 삭제
     * @param goodsId 상품 아이디
     * @param cartId 장바구니 아이디
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "delete from CartItem where goods.id = :goodsId and myCart.id = :cartId")
    void deleteById(@Param("goodsId") String goodsId, @Param("cartId") String cartId);

    /**
     * cartId로 cartItem 삭제
     * @param cartId 장바구니 아이디
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "delete from CartItem where myCart.id = :cartId")
    void deleteCartItem(@Param("cartId") String cartId);
}
