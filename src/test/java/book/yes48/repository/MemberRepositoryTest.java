package book.yes48.repository;

import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @AfterEach
    public void clear() {
        em.clear();
    }

    @Test
    @DisplayName("유저아이디로 유저 찾기")
    public void findUser() {
        // given
        Member member = Member.builder()
                .userId("userId")
                .phone("test")
                .name("테스트")
                .email("test@naver.com")
                .phone("010-1111-1111")
                .postcode("12345")
                .address("서울특별시")
                .detailsAddress("xx구")
                .extraAddress("xx동")
                .state("Y")
                .role(Role.USER)
                .build();

        memberRepository.save(member);

        // when
        Member findUser = memberRepository.findUser("userId");

        // then
        assertThat(findUser.getUserId()).isEqualTo(member.getUserId());
    }
}