package book.yes48.repository;

import book.yes48.entity.FileStore;
import book.yes48.entity.cart.CartItem;
import book.yes48.entity.cart.MyCart;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.repository.cartItem.CartItemRepository;
import book.yes48.repository.goods.GoodsRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Slf4j
@SpringBootTest
@Transactional(readOnly = true)
public class CartItemRepositoryTest {

    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    EntityManager em;
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before("스프링 시큐리티")
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void clear() {
        em.clear();
    }

    @Test
    @DisplayName("장바구니에 중복 상품 등록 방지 - 중복 상품이 없을 때")
    public void checkDuplicationGoods_yesNull() {
        // given
        Member member = getMember(); // 회원 저장 - myCart와 연관관계
        em.persist(member);

        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);  // 상품 저장 - cartItem와 연관관계
        em.persist(goods);

        MyCart createCart = getMyCart(member);  // myCart 생성
        em.persist(createCart);

        // when
        String goodsId = String.valueOf(goods.getId());
        String cartId = String.valueOf(createCart.getId());
        CartItem findCartItem = cartItemRepository.findCartItem(goodsId, cartId);

        // then
        Assertions.assertThat(findCartItem).isNull();
    }

    @Test
    @DisplayName("장바구니에 중복 상품 등록 방지 - 중복 상품이 있을 때")
    public void checkDuplicationGoods_NotNull() {
        // given
        Member member = getMember(); // 회원 저장 - myCart와 연관관계
        em.persist(member);

        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);  // 상품 저장 - cartItem와 연관관계
        em.persist(goods);

        MyCart createCart = getMyCart(member);  // myCart 생성
        em.persist(createCart);

        // when
        CartItem cartItem = CartItem.builder()   // 새로운 cartItem 추가
                .myCart(createCart)
                .goods(goods)
                .build();
        em.persist(cartItem);

        String newGoodsId = String.valueOf(cartItem.getGoods().getId());
        String cartId = String.valueOf(cartItem.getMyCart().getId());
        CartItem findCartItem = cartItemRepository.findCartItem(newGoodsId, cartId);

        // then
        Assertions.assertThat(findCartItem).isNotNull();
    }

    @Test
    @DisplayName("상품 아이디와 카트 아이디를 비교해서 장바구니 내부 아이템 삭제하기")
    @Transactional
    public void deleteById() {
        Member member = getMember(); // 회원 저장 - myCart와 연관관계
        em.persist(member);

        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);  // 상품 저장 - cartItem와 연관관계
        em.persist(goods);

        MyCart createCart = getMyCart(member);  // myCart 생성
        em.persist(createCart);

        CartItem cartItem = CartItem.builder()   // cartItem 추가
                .myCart(createCart)
                .goods(goods)
                .build();
        em.persist(cartItem);

        // when
        String goodsId = String.valueOf(cartItem.getGoods().getId());
        String cartId = String.valueOf(cartItem.getMyCart().getId());
        cartItemRepository.deleteById(goodsId, cartId);

        List<CartItem> all = cartItemRepository.findAll();

        // then
        Assertions.assertThat(all).isNotNull();
    }

    // 테스트 MyCart
    private MyCart getMyCart(Member member) {
        return MyCart.builder()
                .member(member)
                .build();
    }

    // 테스트 회원
    private static Member getMember() {
        return Member.builder()
                .userId("userId")
                .phone("test")
                .name("테스트")
                .email("test@naver.com")
                .phone("010-1111-1111")
                .postcode("12345")
                .basicAddress("서울특별시")
                .detailsAddress("xx구")
                .extraAddress("xx동")
                .state("Y")
                .role(Role.USER)
                .build();
    }

    // 테스트 상품
    private static Goods getGoods(FileStore file) {
        return Goods.builder()
                .name("스프링 부트")
                .sort("국내 도서")
                .author("이동욱")
                .publisher("프리렉")
                .publisherDate("20191129")
                .price(22000)
                .stockQuantity(20)
                .fileStore(file)
                .event("N")
                .state("Y")
                .build();
    }

    // 테스트 상품 파일
    private static FileStore getFile() {
        FileStore fileStore = FileStore.builder()
                .filename("스프링부트와 AWS")
                .filepath("/files/" + UUID.randomUUID())
                .build();

        return fileStore;
    }
}
