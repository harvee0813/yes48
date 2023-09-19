package book.yes48.service;

import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.web.form.member.MemberSaveForm;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional(readOnly = true)
class MemberServiceTest {

    @Autowired MemberService memberService;
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
    void clean() {
        em.clear();
    }

    @Test
    @Transactional
    @DisplayName("회원 등록 확인")
    public void save() {

        // given
        MemberSaveForm form = MemberSaveForm.builder()
                .userId("testId")
                .password("test")
                .name("테스트")
                .email("test@naver.com")
                .phone("01012345678")
                .postcode("12345")
                .basicAddress("서울특별시")
                .detailsAddress("xxx번지")
                .extraAddress("xxx로")
                .role(Role.USER)
                .state("Y")
                .build();

        // when
        Long saveId = memberService.save(form);
        Optional<Member> member = memberService.findById(saveId);

        // then
        assertThat(member.get().getId()).isEqualTo(saveId);
        assertThat(member.get().getUserId()).isEqualTo(form.getUserId());
        assertThat(member.get().getName()).isEqualTo(form.getName());
        assertThat(member.get().getEmail()).isEqualTo(form.getEmail());
        assertThat(member.get().getPhone()).isEqualTo(form.getPhone());
        assertThat(member.get().getPostcode()).isEqualTo(form.getPostcode());
        assertThat(member.get().getBasicAddress()).isEqualTo(form.getBasicAddress());
        assertThat(member.get().getState()).isEqualTo(form.getState());
        assertThat(member.get().getDetailsAddress()).isEqualTo(form.getDetailsAddress());
        assertThat(member.get().getExtraAddress()).isEqualTo(form.getExtraAddress());
        assertThat(member.get().getRole()).isEqualTo(form.getRole());

    }

    @Test
    @DisplayName("회원 등록시 아이디가 중복될 때")
    public void userIdCheck_duplication() {
        // given
        Member member1 = testMember();
        Member member2 = testMember();

        em.persist(member1);

        // when
        String result = memberService.userIdCheck(member2.getUserId());

        // then
        assertThat(result).isEqualTo(null);
    }

    @Test
    @DisplayName("회원 등록시 아이디가 중복되지 않을 때")
    public void userIdCheck_notDuplication() {
        // given
        Member member = testMember();

        // when
        String result = memberService.userIdCheck(member.getUserId());

        // then
        assertThat(result).isEqualTo("ok");
    }

    @Test
    @DisplayName("아이디로 회원 찾기")
    public void findById() {
        // given
        Member member = testMember();
        em.persist(member);

        // when
        Optional<Member> findMember = memberService.findById(member.getId());

        // then
        assertThat(member.getId()).isEqualTo(findMember.get().getId());
    }

    private static Member testMember() {
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
                .build();

        return member;
    }
}