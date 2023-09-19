package book.yes48.service;

import book.yes48.entity.member.Member;
import book.yes48.repository.myPage.MyPageRepository;
import book.yes48.web.form.myPage.AddressUpdateForm;
import book.yes48.web.form.myPage.MyPageInformationForm;
import book.yes48.web.form.myPage.PhoneUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional(readOnly = true)
public class MyPageService {

    @Autowired MyPageRepository myPageRepository;

    /**
     * 핸드폰 번호와 배송 주소 등록 or 수정
     * @param userId 핸드폰 번호와 배송 주소를 수정할 고객
     * @param phoneUpdateForm 핸드폰 번호 수정 폼
     * @param addressUpdateForm 배송 주소 수정 폼
     */
    @Transactional
    public void updateAddressAndPhone(String userId, PhoneUpdateForm phoneUpdateForm, AddressUpdateForm addressUpdateForm) {

        Member findMember = myPageRepository.findUser(userId);

        if (!phoneUpdateForm.getPhone().isEmpty()) {
            findMember.updatePhone(phoneUpdateForm);
        } else if (!addressUpdateForm.getBasicAddress().isEmpty()) {
            findMember.updateAddress(addressUpdateForm);
        } else if (!phoneUpdateForm.getPhone().isEmpty() && !addressUpdateForm.getBasicAddress().isEmpty()) {
            findMember.updateAddressAndPhone(addressUpdateForm, phoneUpdateForm);
        } else {

        }
    }


    /**
     * 아이디로 멤버 찾아서 마이페이지 내정보에 필요한 정보 가공해서 넣기
     * @param userId 찾을 멤버 아이디
     * @return 찾은 멤버
     */
    public MyPageInformationForm findMemberById(String userId) {
        Member findMember = myPageRepository.findUser(userId);

        MyPageInformationForm form = MyPageInformationForm.builder()
                .userId(getUserId(findMember))
                .name(findMember.getName())
                .email(findMember.getEmail())
                .basicAddress(getAddress(findMember))
                .phone(findMember.getPhone())
                .build();

        return form;
    }

    /**
     * userId로 멤버 찾을 때 해당 멤버의 address 조합
     * @param findMember 찾은 멤버
     * @return 조합된 address
     */
    private static String getAddress(Member findMember) {
        String address = findMember.getBasicAddress();
        String detailAddress = findMember.getDetailsAddress();
        String extraAddress = findMember.getExtraAddress();

        String totalAddress = "";

        // extraAddress null 여부에 따라 totalAddress 반환 값이 달라진다.
        if (extraAddress != null) {
            totalAddress = address + " " + detailAddress + " " + extraAddress;
        } else {
            totalAddress = address + " " + detailAddress;
        }

        // 만약 address와 detailAddress에 null이 포함되면 totalAddress는 빈 값을 가진다.
        if (totalAddress.contains("null")) {
            totalAddress = "";
        }

        return totalAddress;
    }

    /**
     * 네이버와 구글로 로그인한 고객 아이디 "네이버 로그인" 혹은 "구글 로그인"으로 기재하기
     * @param findMember 찾은 맴버
     * @return "네이버 로그인" 혹은 "구글 로그인"
     */
    private static String getUserId(Member findMember) {
        String userId = findMember.getUserId();

        if (userId.contains("naver")) {
            userId = "네이버 로그인";
        } else if (userId.contains("google")) {
            userId = "구글 로그인";
        }

        return userId;
    }

    @Transactional
    public void updateState(String userId) {
        Member member = myPageRepository.findUser(userId);

        member.withdraw("N");
    }
}
