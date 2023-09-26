package book.yes48.repository;

import book.yes48.entity.cart.MyCart;
import book.yes48.entity.member.Member;
import book.yes48.repository.myCart.MyCartRepository;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

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
        Member member = em.find(Member.class, 117);

        // when
        MyCart myCart = myCartRepository.findMyCart(member);

        // then
        assertThat(member.getId()).isEqualTo(myCart.getMember().getId());
    }

    @Test
    @DisplayName("장바구니에 상품을 담기 위해 회원 아이디로 장바구니 찾기")
    public void findMyCartById() {
        // given
        Member member = em.find(Member.class, 117);
        MyCart myCart = em.find(MyCart.class, 63);

        // when
        String userId = String.valueOf(member.getUserId());
        MyCart myCartById = myCartRepository.findMyCartById(userId);

        // then
        assertThat(myCartById.getMember().getId()).isEqualTo(myCart.getMember().getId());
    }
}
