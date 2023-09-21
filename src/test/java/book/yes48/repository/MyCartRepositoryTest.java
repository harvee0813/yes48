package book.yes48.repository;

import book.yes48.entity.FileStore;
import book.yes48.entity.cart.CartItem;
import book.yes48.entity.cart.MyCart;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.repository.myCart.MyCartRepository;
import book.yes48.security.auth.PrincipleDetails;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional(readOnly = true)
public class MyCartRepositoryTest {

    @Autowired
    MyCartRepository myCartRepository;
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
    @DisplayName("장바구니 담기 클릭시, 장바구니 첫 생성을 위한 회원 개인 장바구니 찾기")
    public void findMyCart() {
        // given
        Member member = getMember(); // 회원 저장 - myCart와 연관관계
        em.persist(member);

        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);  // 상품 저장 - cartItem와 연관관계
        em.persist(goods);

        MyCart createCart = getMyCart(member);  // myCart 생성
        em.persist(createCart);

        CartItem cartItem = getCartItem(goods, createCart); // cartItem에 goods 저장 - myCart와 연관관계
        em.persist(cartItem);

        Optional<MyCart> findCartById = myCartRepository.findById(createCart.getId()); // 장바구니에 cartItem 저장
        findCartById.orElseThrow().setCartItems(cartItem);

        // when
        MyCart myCart = myCartRepository.findMyCart(member);

        // then
        assertThat(member.getId()).isEqualTo(myCart.getMember().getId());
    }

    @Test
    @DisplayName("장바구니에 상품을 담기 위해 회원 아이디로 장바구니 찾기")
    public void findMyCartById() {
        // given
        Member member = getMember(); // 회원 저장 - myCart와 연관관계
        em.persist(member);

        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);  // 상품 저장 - cartItem와 연관관계
        em.persist(goods);

        MyCart createCart = getMyCart(member);  // myCart 생성
        em.persist(createCart);

        CartItem cartItem = getCartItem(goods, createCart); // cartItem에 goods 저장 - myCart와 연관관계
        em.persist(cartItem);

        Optional<MyCart> findCartById = myCartRepository.findById(createCart.getId()); // 장바구니에 cartItem 저장
        findCartById.orElseThrow().setCartItems(cartItem);

        // when
        String userId = String.valueOf(member.getUserId());
        MyCart myCartById = myCartRepository.findMyCartById(userId);

        // then
        assertThat(myCartById.getMember().getId()).isEqualTo(findCartById.get().getMember().getId());
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
