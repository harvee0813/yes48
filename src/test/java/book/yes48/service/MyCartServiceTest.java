package book.yes48.service;

import book.yes48.entity.FileStore;
import book.yes48.entity.cart.CartItem;
import book.yes48.entity.cart.MyCart;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.repository.cartItem.CartItemRepository;
import book.yes48.repository.myCart.MyCartRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assert;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Slf4j
@SpringBootTest
@Transactional
class MyCartServiceTest {

    @Autowired MyCartService myCartService;
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
    @DisplayName("장바구니에 상품 등록 - 이미 등록된 상품을 등록할 경우")
    public void setCartItem_yesItem() {
        // given
        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);  // 상품 저장 - cartItem와 연관관계
        em.persist(goods);

        Member member = getMember(); // 회원 생성 - myCart와 연관관계
        em.persist(member);

        MyCart createCart = getMyCart(member);  // myCart 생성
        em.persist(createCart);

        CartItem cartItem = CartItem.builder()   // 동일한 cartItem 미리 등록
                .myCart(createCart)
                .goods(goods)
                .build();
        em.persist(cartItem);

        // when
        String goodsId = String.valueOf(goods.getId());
        String userId = member.getUserId();
        String count = "1";

        String result = myCartService.setCartItem(goodsId, count, userId);

        // then
        assertThat(result).isEqualTo(null);
    }

    @Test
    @DisplayName("장바구니에 상품 등록 - 등록되지 않은 상품을 등록할 경우")
    public void setCartItem_noItem() {
        // given
        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);  // 상품 저장 - cartItem와 연관관계
        em.persist(goods);

        Member member = getMember(); // 회원 생성 - myCart와 연관관계
        em.persist(member);

        MyCart createCart = getMyCart(member);  // myCart 생성
        em.persist(createCart);

        // when
        String goodsId = String.valueOf(goods.getId());
        String userId = member.getUserId();
        String count = "1";

        String result = myCartService.setCartItem(goodsId, count, userId);

        // then
        assertThat(result).isEqualTo("ok");
    }

    @Test
    @DisplayName("장바구니 상품 삭제")
    public void deleteCartItem() {
        // given
        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);  // 상품 저장 - cartItem와 연관관계
        em.persist(goods);

        Member member = getMember(); // 회원 생성 - myCart와 연관관계
        em.persist(member);

        MyCart createCart = getMyCart(member);  // myCart 생성
        em.persist(createCart);

        CartItem cartItem = getCartItem(goods, createCart); // 장바구니 상품 등록
        em.persist(cartItem);

        // when
        String goodsId = String.valueOf(goods.getId());
        String userId = member.getUserId();

        String result = myCartService.deleteCartItem(goodsId, userId);

        // then
        assertThat(result).isEqualTo("ok");
    }

    @Test
    @DisplayName("장바구니 상품 수량 변경")
    public void updateQuantity() {
        // given
        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);  // 상품 저장 - cartItem와 연관관계
        em.persist(goods);

        Member member = getMember(); // 회원 생성 - myCart와 연관관계
        em.persist(member);

        MyCart createCart = getMyCart(member);  // myCart 생성
        em.persist(createCart);

        CartItem cartItem = CartItem.builder()   // 동일한 cartItem 미리 등록
                .myCart(createCart)
                .goods(goods)
                .quantity(1)
                .build();
        em.persist(cartItem);

        // when
        String quantity = "3";
        String goodsId = String.valueOf(goods.getId());
        String userId = member.getUserId();

        String result = myCartService.updateQuantity(goodsId, quantity, userId);

        // then
        assertThat(result).isEqualTo("ok");
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

    // 테스트 cartItem
    private static CartItem getCartItem(Goods goods, MyCart createCart) {
        return CartItem.builder()
                .myCart(createCart)
                .goods(goods)
                .quantity(2)
                .build();
    }
}