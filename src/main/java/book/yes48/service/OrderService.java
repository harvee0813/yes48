package book.yes48.service;

import book.yes48.entity.cart.CartItem;
import book.yes48.entity.cart.MyCart;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import book.yes48.entity.order.Delivery;
import book.yes48.entity.order.Order;
import book.yes48.entity.order.OrderGoods;
import book.yes48.repository.cartItem.CartItemRepository;
import book.yes48.repository.goods.GoodsRepository;
import book.yes48.repository.member.MemberRepository;
import book.yes48.repository.myCart.MyCartRepository;
import book.yes48.repository.order.orderGoods.OrderGoodsRepository;
import book.yes48.repository.order.OrderRepository;
import book.yes48.web.form.myPage.OrderHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    @Autowired
    private final OrderRepository orderRepository;
    
    /**
     * 장바구니에 담긴 상품 orderGoods(주문 대기 상품)로 이전
     * @param userId
     * @return
     */
    @Transactional
    public List<OrderGoods> getGoods(String userId) {

        // 주문 대기 상품 리스트가 없으면 null 반환
        List<OrderGoods> findOrderGoods = orderGoodsRepository.findOrderGoodsByUserId(userId, "WAIT");
        if (findOrderGoods == null) {
            return null;
        }

        // cartItem에서 orderGoods로 상품 이전
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
        List<OrderGoods> all = orderGoodsRepository.findAllByMemberId(memberId, "WAIT");

        return all;
    }

    /**
     * 상품 상세에서 바로 구매 클릭시 상품 등록
     * @param count 상품 수량
     * @param goodsId 상품 아이디
     * @param memberPkId 유저의 고유 아이디 (pk)
     * @param userId 로그인한 유저 아이디
     * @return
     */
    @Transactional
    public String GoodsBuyNow(String count, String goodsId, String userId, String memberPkId) {

        // orderGoods가 이미 들어있다면 관련된 goods 삭제
        List<OrderGoods> findOrderGoods = orderGoodsRepository.findOrderGoodsByUserId(userId, "WAIT");

        if (!findOrderGoods.isEmpty()) {
            orderGoodsRepository.deleteByMemberId(memberPkId, String.valueOf("WAIT"));
        }

        // 장바구니에 상품 들어있으면 장바구니 상품 삭제
        Optional<Member> fondMember = memberRepository.findById(Long.valueOf(memberPkId));
        Member member = fondMember.get();

        MyCart findCart = myCartRepository.findMyCart(member);
        
        if (findCart != null) {
            cartItemRepository.deleteCartItem(String.valueOf(findCart.getId()));
        }

        // orderGoods에 상품 등록
        Member findMember = memberRepository.findUser(userId);
        Goods findGoods = goodsRepository.findGoodsById(goodsId);

        OrderGoods orderGoods = OrderGoods.builder()
                .goods(findGoods)
                .member(findMember)
                .quantity(Integer.parseInt(count))
                .price(findGoods.getPrice())
                .build();

        orderGoodsRepository.save(orderGoods);
        
        return "ok";
    }

    /**
     * 주문 생성
     * @param orderPrice 전체 주문 가격
     * @param address 주문자 주소
     * @param userId 주문자 아이디
     * @return
     */
    @Transactional
    public String createOrder(String orderPrice, String address, String userId) {

        // 주문 생성에 필요한 정보
        Member findMember = memberRepository.findUser(userId);
        List<OrderGoods> findOrderGoods = orderGoodsRepository.findOrderGoodsByUserId(userId, "WAIT");

        // 배송 주소
        Delivery delivery = Delivery.builder()
                .address(address)
                .build();

        Order order = Order.builder()
                .member(findMember)
                .delivery(delivery)
                .state("ORDER")
                .totalPrice(Integer.parseInt(orderPrice))
                .build();

        orderRepository.save(order);

        // 주문
        for (int i = 0; i < findOrderGoods.size(); i++) {

            // 주문 상품 수량 변경
            Long goodsId = findOrderGoods.get(i).getGoods().getId();
            Long memberId = findMember.getId();

            OrderGoods orderGoods = orderGoodsRepository.findByGoodsId(goodsId, "WAIT", memberId);
            log.info("orderGoods.getId() = {}", orderGoods.getId());

            Optional<Goods> goods = goodsRepository.findById(Long.valueOf(goodsId));

            // 수량 계산
            int stockQuantity = goods.get().getStockQuantity();
            int minusQuantity = orderGoods.getQuantity();
            int updateQuantity = stockQuantity - minusQuantity;
            if (updateQuantity > 0) {
                goods.orElseThrow().updateQuantity(updateQuantity);
            } else {
                return null;
            }
        }

        // orderGoods 주문 대기 상품 상태 'WAIT' 변경 & order로 update
        for (int i = 0; i < findOrderGoods.size(); i++) {
            findOrderGoods.get(i).updateOrder(order);
            findOrderGoods.get(i).updateState("ORDER");
        }

        return "ok";
    }

    /**
     * 마이페이지 - 주문 목록
     * @param pageable 페이징
     * @return
     */
    public Page<OrderHistoryDto> getOrderList(Pageable pageable, Long memberPkId) {

        Page<OrderHistoryDto> orderList = orderGoodsRepository.findOrderList(pageable, memberPkId);

        return orderList;
    }
}
