package book.yes48.service;

import book.yes48.entity.FileStore;
import book.yes48.entity.cart.CartItem;
import book.yes48.entity.cart.MyCart;
import book.yes48.entity.goods.Goods;
import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.entity.order.OrderGoods;
import jakarta.persistence.EntityManager;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional(readOnly = true)
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
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
    @DisplayName("주문 대기 상품 등록")
    public void getGoods() {
        // given
        Member member = em.find(Member.class, 117);
        String userId = member.getUserId();

        // when
        List<OrderGoods> findOrderGoods = orderService.getGoods(userId);

        // then
        assertThat(findOrderGoods).isNotEmpty();
        assertThat(findOrderGoods.get(0).getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("바로 구매 클릭시 주문 대기 상품 등록")
    public void GoodsBuyNow() {
        // given
        Goods goods = em.find(Goods.class, 113);
        Member member = em.find(Member.class, 117);

        String goodsId = String.valueOf(goods.getId());
        String userId = member.getUserId();
        String count = "1";
        String memberPkId = String.valueOf(member.getId());

        // when
        String result = orderService.GoodsBuyNow(count, goodsId, userId, memberPkId);

        // then
        assertThat(result).isEqualTo("ok");

    }
}
