package book.yes48.service;

import book.yes48.entity.cart.CartItem;
import book.yes48.entity.cart.MyCart;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import book.yes48.repository.cartItem.CartItemRepository;
import book.yes48.repository.goods.GoodsRepository;
import book.yes48.repository.member.MemberRepository;
import book.yes48.repository.myCart.MyCartRepository;
import book.yes48.web.form.cartItemDto.CartItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MyCartService {

    @Autowired private final GoodsRepository goodsRepository;
    @Autowired private final MemberRepository memberRepository;
    @Autowired private final MyCartRepository myCartRepository;
    @Autowired private final CartItemRepository cartItemRepository;

    /**
     * 장바구니에 담긴 상품 전체 가져오기
     * @param userId 유저 아이디
     * @return
     */
    @Transactional
    public List<CartItemDto> findAllCartItem(String userId) throws Exception {

        MyCart findMyCart = myCartRepository.findMyCartById(userId);
        List<CartItemDto> findCartItem = cartItemRepository.findCartById(findMyCart.getId());

        return findCartItem;
    }

    /**
     * 장바구니 담기
     * @param goodsId 상품 아이디
     * @param count 수량
     * @param userId 유저 아이디
     * @return
     */
    @Transactional
    public String setCartItem(String goodsId, String count, String userId) {

        // 로그인 페이지로 이동
        if (userId == null) {
            return "err";
        }

        int quantity = Integer.parseInt(count);

        Goods findgoods = goodsRepository.findGoodsById(goodsId);
        Member findMember = memberRepository.findUser(userId);
        MyCart myCart = myCartRepository.findMyCart(findMember);

        CartItem cartItem = CartItem.builder()
                .goods(findgoods)
                .myCart(myCart)
                .quantity(quantity)
                .build();

        String checkGoodsId = String.valueOf(findgoods.getId());
        String cartId = String.valueOf(myCart.getId());
        CartItem result = cartItemRepository.findCartItem(checkGoodsId, cartId);
        log.info("result = {}", result);

        if (result != null) {
            return null;
        }

        cartItemRepository.save(cartItem);

        return "ok";
    }

    /**
     * 상품 삭제
     * @param goodsId 삭제할 상품 아이디
     * @param userId 유저 아이디
     * @return
     */
    @Transactional
    public String deleteCartItem(String goodsId, String userId) {
        if (goodsId == null || userId == null) {
            return null;
        }

        Member findMember = memberRepository.findUser(userId);
        MyCart findCart = myCartRepository.findMyCart(findMember);

        String findCartId = String.valueOf(findCart.getId());

        cartItemRepository.deleteById(goodsId, findCartId);

        return "ok";
    }

    /**
     * 상품 별 수량 변경
     * @param goodsId 수량 변경할 상품 아이디
     * @param quantity 변경할 수량
     * @param userId 유저 아이디
     * @return
     */
    @Transactional
    public String updateQuantity(String goodsId, String quantity, String userId) {
        if (goodsId == null || userId == null) {
            return null;
        }

        Member findMember = memberRepository.findUser(userId);
        MyCart findCart = myCartRepository.findMyCart(findMember);

        String findCartId = String.valueOf(findCart.getId());
        CartItem findCartItem = cartItemRepository.findCartItem(goodsId, findCartId);

        findCartItem.setQuantity(Integer.parseInt(quantity));

        return "ok";
    }
}
