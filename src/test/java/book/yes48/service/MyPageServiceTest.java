package book.yes48.service;

import book.yes48.entity.member.Member;
import book.yes48.entity.member.Role;
import book.yes48.repository.myPage.MyPageRepository;
import book.yes48.web.form.myPage.AddressUpdateForm;
import book.yes48.web.form.myPage.MyPageInformationForm;
import book.yes48.web.form.myPage.PhoneUpdateForm;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
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
class MyPageServiceTest {

    @Autowired
    MyPageRepository myPageRepository;
    @Autowired
    MyPageService myPageService;
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

    @BeforeEach
    public void sample() {
        Member member = Member.builder()
                .userId("testId")
                .password("test")
                .name("테스트")
                .email("test@naver.com")
                .phone("01012345678")
                .basicAddress("서울특별시")
                .postcode("12345")
                .detailsAddress("xxx번지")
                .extraAddress("xxx로")
                .role(Role.USER)
                .state("Y")
                .providerId("google 아이디")
                .provider("google")
                .build();

        myPageRepository.save(member);
    }

    @AfterEach
    public void clear() {
        em.clear();
    }

    @Test
    @Transactional
    @DisplayName("배송 주소와 핸드폰 번호 중 배송 주소만 업데이트 할 때")
    public void updateAddress() {
        // given
        String userId = "testId";

        AddressUpdateForm addressUpdateForm = AddressUpdateForm.builder()
                .basicAddress("서울특별시")
                .postcode("12345")
                .detailsAddress("xxx번지")
                .extraAddress("xxx로")
                .build();

        PhoneUpdateForm phoneUpdateForm = PhoneUpdateForm.builder()
                .phone("")
                .build();

        // when
        myPageService.updateAddressAndPhone(userId, phoneUpdateForm, addressUpdateForm);

        // then
        Member findMember = myPageRepository.findUser(userId);

        assertThat(findMember.getBasicAddress()).isEqualTo(addressUpdateForm.getBasicAddress());               // address 비교
        assertThat(findMember.getPostcode()).isEqualTo(addressUpdateForm.getPostcode());             // postcode 비교
        assertThat(findMember.getDetailsAddress()).isEqualTo(addressUpdateForm.getDetailsAddress()); // detailsAddress 비교
        assertThat(findMember.getExtraAddress()).isEqualTo(addressUpdateForm.getExtraAddress());     // extraAddress 비교

        assertThat(findMember.getPhone()).isNotEqualTo(phoneUpdateForm.getPhone());                  // phone 비교
    }

    @Test
    @Transactional
    @DisplayName("배송 주소와 핸드폰 번호 중 핸드폰 번호만 업데이트 할 때")
    public void updatePhone() {
        // given
        String userId = "testId";

        AddressUpdateForm addressUpdateForm = AddressUpdateForm.builder()
                .basicAddress("")
                .postcode("")
                .detailsAddress("")
                .extraAddress("")
                .build();

        PhoneUpdateForm phoneUpdateForm = PhoneUpdateForm.builder()
                .phone("010-1111-1111")
                .build();

        // when
        myPageService.updateAddressAndPhone(userId, phoneUpdateForm, addressUpdateForm);

        // then
        Member findMember = myPageRepository.findUser(userId);

        assertThat(findMember.getPhone()).isEqualTo(phoneUpdateForm.getPhone());                        // phone 비교

        assertThat(findMember.getBasicAddress()).isNotEqualTo(addressUpdateForm.getBasicAddress());               // address 비교
        assertThat(findMember.getPostcode()).isNotEqualTo(addressUpdateForm.getPostcode());             // postcode 비교
        assertThat(findMember.getDetailsAddress()).isNotEqualTo(addressUpdateForm.getDetailsAddress()); // detailsAddress 비교
        assertThat(findMember.getExtraAddress()).isNotEqualTo(addressUpdateForm.getExtraAddress());     // extraAddress 비교
    }

    @Test
    @Transactional
    @DisplayName("배송 주소와 핸드폰 번호 둘다 업데이트 할 때")
    public void updateAddressAndPhone() {
        // given
        String userId = "testId";

        AddressUpdateForm addressUpdateForm = AddressUpdateForm.builder()
                .basicAddress("서울특별시")
                .postcode("12345")
                .detailsAddress("xxx번지")
                .extraAddress("xxx로")
                .build();

        PhoneUpdateForm phoneUpdateForm = PhoneUpdateForm.builder()
                .phone("010-1111-1111")
                .build();

        // when
        myPageService.updateAddressAndPhone(userId, phoneUpdateForm, addressUpdateForm);

        // then
        Member findMember = myPageRepository.findUser(userId);

        assertThat(findMember.getPhone()).isEqualTo(phoneUpdateForm.getPhone());                        // phone 비교
        assertThat(findMember.getBasicAddress()).isEqualTo(addressUpdateForm.getBasicAddress());               // address 비교
        assertThat(findMember.getPostcode()).isEqualTo(addressUpdateForm.getPostcode());             // postcode 비교
        assertThat(findMember.getDetailsAddress()).isEqualTo(addressUpdateForm.getDetailsAddress()); // detailsAddress 비교
        assertThat(findMember.getExtraAddress()).isEqualTo(addressUpdateForm.getExtraAddress());     // extraAddress 비교

    }

    @Test
    @DisplayName("아이디로 멤버 찾아서 마이페이지 내정보에 필요한 정보 가공해서 넣기 - 기존 유저")
    public void findMemberById() {
        // given
        String userId = "testId";

        // when
        MyPageInformationForm findMember = myPageService.findMemberById(userId);

        // then
        assertThat(findMember.getUserId()).isEqualTo("testId");
        assertThat(findMember.getName()).isEqualTo("테스트");
        assertThat(findMember.getEmail()).isEqualTo("test@naver.com");
        assertThat(findMember.getBasicAddress()).isEqualTo("서울특별시 xxx번지 xxx로");
        assertThat(findMember.getPhone()).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("아이디로 멤버 찾아서 마이페이지 내정보에 필요한 정보 가공해서 넣기 - 구글 유저")
    public void findMemberById_googleUser() {
        // given
        Member member = Member.builder()
                .userId("google_DDF76A8G098H0A3L")
                .password("test")
                .name("테스트")
                .email("test@naver.com")
                .phone("01012345678")
                .basicAddress("서울특별시")
                .postcode("12345")
                .detailsAddress("xxx번지")
                .extraAddress("xxx로")
                .role(Role.USER)
                .state("Y")
                .providerId("google 아이디")
                .provider("google")
                .build();

        em.persist(member);

        // when
        MyPageInformationForm findMember = myPageService.findMemberById(member.getUserId());

        // then
        assertThat(findMember.getUserId()).isEqualTo("구글 로그인");
    }
}