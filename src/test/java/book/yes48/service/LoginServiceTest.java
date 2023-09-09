package book.yes48.service;

import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.web.form.login.UpdatePasswordForm;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
class LoginServiceTest {

    @Autowired LoginService loginService;
    @Autowired
    EntityManager em;

    @AfterEach
    public void clear() {
        em.clear();
    }

    @Test
    @DisplayName("DB에 등록된 이름과 이메일로 아이디 찾기 ")
    public void searchNameAndEmail() {
        // given
        Member member = testMember();
        em.persist(member);

        // when
        String result = loginService.searchNameAndEmail(member.getName(), member.getEmail());

        // then
        assertThat(result).isEqualTo(member.getUserId());

    }

    @Test
    @DisplayName("DB에 등록되지 않은 이름과 이메일로 아이디 찾기 ")
    public void searchNameAndEmail_null() {
        // given
        Member member = testMember();
        em.persist(member);

        // when
        String name = "테스트입니다.";
        String email = "테스트@naver.com";
        String result = loginService.searchNameAndEmail(name, email);

        // then
        assertThat(result).isEqualTo(null);

    }

    @Test
    @DisplayName("DB에 등록된 이름과 핸드폰 번호로 아이디 찾기 ")
    public void searchNameAndPhone() {
        // given
        Member member = testMember();
        em.persist(member);

        // when
        String result = loginService.searchNameAndPhone(member.getName(), member.getPhone());

        // then
        assertThat(result).isEqualTo(member.getUserId());

    }

    @Test
    @DisplayName("DB에 등록되지 않은 이름과 핸드폰 번호로 아이디 찾기 ")
    public void searchNameAndPhone_null() {
        // given
        Member member = testMember();
        em.persist(member);

        // when
        String name = "테스트입니다.";
        String phone = "010-xxxx-xxxx";
        String result = loginService.searchNameAndPhone(name, phone);

        // then
        assertThat(result).isEqualTo(null);

    }

    @Test
    @Transactional
    @DisplayName("비밀번호 변경")
    public void updateMember() {
        // given
        Member member = testMember();
        em.persist(member);

        UpdatePasswordForm form = UpdatePasswordForm.builder()
                .userId("testId")
                .password("1234")
                .build();

        // when
        Member findMember = loginService.updateMember(form);

        // then
        assertThat(findMember.getPassword()).isEqualTo(form.getPassword());
    }

    // 테스트 전용 Member
    private static Member testMember() {
        Member member = Member.builder()
                .userId("testId")
                .password("test")
                .name("테스트")
                .email("test@naver.com")
                .phone("010-1234-5678")
                .address("12345")
                .postcode("서울특별시")
                .detailsAddress("xxx번지")
                .extraAddress("xxx로")
                .role(Role.USER)
                .state("Y")
                .build();

        return member;
    }
}