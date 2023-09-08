package book.yes48.service;

import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.form.member.MemberSaveForm;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @AfterEach
    void clean() {
        em.clear();
    }

    @Test
    @Transactional
    @DisplayName("회원 등록 확인")
    public void 회원등록() {

        // given
        MemberSaveForm form = MemberSaveForm.builder()
                .userId("testId")
                .password("test")
                .name("테스트")
                .email("test@naver.com")
                .phone("01012345678")
                .postcode("12345")
                .address("서울특별시")
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
        assertThat(member.get().getAddress()).isEqualTo(form.getAddress());
        assertThat(member.get().getState()).isEqualTo(form.getState());
        assertThat(member.get().getDetailsAddress()).isEqualTo(form.getDetailsAddress());
        assertThat(member.get().getExtraAddress()).isEqualTo(form.getExtraAddress());
        assertThat(member.get().getRole()).isEqualTo(form.getRole());

    }

    @Test
    @DisplayName("회원 등록시 아이디가 중복될 때")
    public void 아이디_중복될_때() {
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
    public void 아이디_중복아닐_때() {
        // given
        Member member = testMember();

        // when
        String result = memberService.userIdCheck(member.getUserId());

        // then
        assertThat(result).isEqualTo("ok");
    }

    @Test
    @DisplayName("아이디로 회원 찾기")
    public void id로_회원_찾기() {
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