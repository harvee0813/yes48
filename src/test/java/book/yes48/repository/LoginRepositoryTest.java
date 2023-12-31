package book.yes48.repository;

import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.repository.login.LoginRepository;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional(readOnly = true)
public class LoginRepositoryTest {

    @Autowired LoginRepository loginRepository;
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

    @BeforeEach
    public void sample() {
        Member member = Member.builder()
                .userId("testId")
                .password("test")
                .name("테스트")
                .email("test@naver.com")
                .phone("01012345678")
                .basicAddress("12345")
                .postcode("서울특별시")
                .detailsAddress("xxx번지")
                .extraAddress("xxx로")
                .role(Role.USER)
                .state("Y")
                .providerId("google 아이디")
                .provider("google")
                .build();

        loginRepository.save(member);
    }

    @Test
    @DisplayName("이름과 이메일로 Member 찾기")
    public void findMember() {
        // given
        String name = "테스트";
        String email = "test@naver.com";
        String state = "Y";

        // when
        Member findMember = loginRepository.findMember(name, email, state);

        // then
        Assertions.assertThat(findMember.getName()).isEqualTo(name);
        Assertions.assertThat(findMember.getEmail()).isEqualTo(email);
        Assertions.assertThat(findMember.getState()).isEqualTo(state);
    }

    @Test
    @DisplayName("이름과 핸드폰 번호로 Member 찾기")
    public void findUserByPhone() {
        // given
        String name = "테스트";
        String phone = "01012345678";
        String state = "Y";

        // when
        Member findMember = loginRepository.findMemberByPhone(name, phone, state);

        // then
        Assertions.assertThat(findMember.getName()).isEqualTo(name);
        Assertions.assertThat(findMember.getPhone()).isEqualTo(phone);
        Assertions.assertThat(findMember.getState()).isEqualTo(state);
    }

    @Test
    @DisplayName("이름과 유저아이디로 Member 찾기")
    public void checkNameAndUserId() {
        // given
        String name = "테스트";
        String userId = "testId";
        String state = "Y";

        // when
        Member findMember = loginRepository.checkNameAndUserId(name, userId, state);

        // then
        Assertions.assertThat(findMember.getName()).isEqualTo(name);
        Assertions.assertThat(findMember.getUserId()).isEqualTo(userId);
        Assertions.assertThat(findMember.getState()).isEqualTo(state);
    }

    @Test
    @DisplayName("유저아이디로 Member 찾기")
    public void finByMember() {
        // given
        String userId = "testId";

        // when
        Member findMember = loginRepository.findMemberById(userId);

        // then
        Assertions.assertThat(findMember.getUserId()).isEqualTo(userId);
    }
}
