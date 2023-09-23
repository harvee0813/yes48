package book.yes48.repository;

import book.yes48.entity.FileStore;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.entity.order.OrderGoods;
import book.yes48.repository.order.orderGoods.OrderGoodsRepository;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional(readOnly = true)
public class OrderGoodsRepositoryTest {

    @Autowired
    OrderGoodsRepository orderGoodsRepository;
    @Autowired
    EntityManager em;
    @Autowired
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
    @DisplayName("장바구니에 담긴 상품 주문 대기 상태 상품으로 가져오기")
    public void findOrderGoodsByUserId() {
        // given
        Member member = em.find(Member.class, 117);

        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);
        em.persist(goods);

        OrderGoods orderGoods = OrderGoods.builder()
                .member(member)
                .goods(goods)
                .price(goods.getPrice())
                .quantity(1)
                .build();
        em.persist(orderGoods);

        // when
        String userId = member.getUserId();
        List<OrderGoods> findOrderGoodsList = orderGoodsRepository.findOrderGoodsByUserId(userId, "WAIT");

        // then
        assertThat(orderGoods.getId()).isEqualTo(findOrderGoodsList.get(0).getId());
    }

    @Test
    @DisplayName("member pk id 장바구니에 담긴 상품 주문 대기 상태 상품으로 가져오기")
    public void findAllByMemberId() {
        // given
        Member member = em.find(Member.class, 117);

        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);
        em.persist(goods);

        OrderGoods orderGoods = OrderGoods.builder()
                .member(member)
                .goods(goods)
                .price(goods.getPrice())
                .quantity(1)
                .build();
        em.persist(orderGoods);

        // when
        String memberId = String.valueOf(member.getId());
        List<OrderGoods> findOrderGoodsList = orderGoodsRepository.findAllByMemberId(memberId, "WAIT");

        // then
        assertThat(orderGoods.getId()).isEqualTo(findOrderGoodsList.get(0).getId());
    }

    @Test
    @Transactional
    @DisplayName("member pk id로 장바구니에 담긴 상품 주문 대기 상태 상품으로 삭제")
    public void deleteByMemberId() {
        // given
        Member member = em.find(Member.class, 117);

        FileStore fileStore = getFile();
        Goods goods = getGoods(fileStore);
        em.persist(goods);

        OrderGoods orderGoods = OrderGoods.builder()
                .member(member)
                .goods(goods)
                .price(goods.getPrice())
                .quantity(1)
                .build();
        em.persist(orderGoods);

        // when
        String memberId = String.valueOf(member.getId());
        orderGoodsRepository.deleteByMemberId(memberId, "WAIT");

        List<OrderGoods> allByMemberId = orderGoodsRepository.findAllByMemberId(memberId, "WAIT");

        // then
        assertThat(allByMemberId.size()).isEqualTo(0);
    }

    // 테스트 회원
    private static Member getMember() {
        return Member.builder()
                .userId("testId")
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
