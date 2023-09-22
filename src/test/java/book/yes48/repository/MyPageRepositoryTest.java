package book.yes48.repository;

import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.repository.myPage.MyPageRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional(readOnly = true)
class MyPageRepositoryTest {

    @Autowired
    MyPageRepository myPageRepository;
    @Autowired EntityManager em;
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
    @DisplayName("유저아이디로 유저 찾기")
    public void findUser() {
        // given
        Member member = Member.builder()
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

        myPageRepository.save(member);

        // when
        String userId = member.getUserId();
        Member findUser = myPageRepository.findUser(userId);

        // then
        assertThat(findUser.getUserId()).isEqualTo(member.getUserId());
    }

}