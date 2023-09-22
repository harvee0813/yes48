package book.yes48.service;

import book.yes48.entity.cart.CartItem;
import book.yes48.entity.cart.MyCart;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import book.yes48.entity.order.OrderGoods;
import book.yes48.repository.cartItem.CartItemRepository;
import book.yes48.repository.goods.GoodsRepository;
import book.yes48.repository.member.MemberRepository;
import book.yes48.repository.myCart.MyCartRepository;
import book.yes48.repository.order.OrderGoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    @Autowired
    private final MyCartRepository myCartRepository;
    @Autowired
    private final CartItemRepository cartItemRepository;
    @Autowired
    private final OrderGoodsRepository orderGoodsRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final GoodsRepository goodsRepository;
    
    /**
     * 장바구니에 담긴 상품 orderGoods(주문 대기 상품)로 이전
     * @param userId
     * @return
     */
    public List<OrderGoods> getGoods(String userId) {

        // 주문 대기 상품 리스트가 없으면 null 반환
        List<OrderGoods> orderGoodsByUserId = orderGoodsRepository.findOrderGoodsByUserId(userId);
        if (orderGoodsByUserId == null) {
            return null;
        }

        // cartItem에서 orderGodos로 상품 이전
        MyCart findMyCart = myCartRepository.findMyCartById(userId);
        List<CartItem> cartItems = findMyCart.getCartItems();

        for (CartItem cartItem : cartItems) {

            OrderGoods orderGoods = OrderGoods.builder()
                    .goods(cartItem.getGoods())
                    .member(findMyCart.getMember())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getGoods().getPrice())
                    .build();

            orderGoodsRepository.save(orderGoods);

        }

        // orderGoods로 상품 이전 후 cartItem의 상품 삭제
        String cartId = String.valueOf(findMyCart.getId());
        cartItemRepository.deleteCartItem(cartId);

        // memberId에 따른 orderGoods 상품 찾기
        String memberId = String.valueOf(findMyCart.getMember().getId());
        List<OrderGoods> all = orderGoodsRepository.findAllByMemberId(memberId);

        return all;
    }

    /**
     * detailBook.html과 detailMusic.html에서 바로 구매 클릭시 상품 등록
     * @param count 상품 수량
     * @param goodsId 상품 아이디
     * @param memberPkId 유저의 고유 아이디 (pk)
     * @param userId 로그인한 유저 아이디
     * @return
     */
    public String GoodsBuyNow(String count, String goodsId, String userId, String memberPkId) {

        // orderGoods가 이미 들어있다면 관련된 goods를 지운다.
        List<OrderGoods> orderGoodsByUserId = orderGoodsRepository.findOrderGoodsByUserId(userId);
        if (!orderGoodsByUserId.isEmpty()) {
            orderGoodsRepository.deleteByMemberId(memberPkId);
        }

        // orderGoods에 상품 등록
        Member findMember = memberRepository.findUser(userId);
        log.info("findMember.getId() = {}", findMember.getId());

        Goods findGoods = goodsRepository.findGoodsById(goodsId);
        log.info("findGoods.getId() = {}", findGoods.getId());
        
        OrderGoods orderGoods = OrderGoods.builder()
                .goods(findGoods)
                .member(findMember)
                .quantity(Integer.parseInt(count))
                .price(findGoods.getPrice())
                .build();

        orderGoodsRepository.save(orderGoods);
        
        return "ok";
    }
}
