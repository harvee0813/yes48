package book.yes48.service;

import book.yes48.entity.member.Member;
import book.yes48.web.form.login.UpdatePasswordForm;
import book.yes48.repository.login.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private final LoginRepository loginRepository;

    /**
     * 이름과 이메일로 아이디 찾기
     * @return
     */
    public String searchNameAndEmail(String name, String email) {
        // 회원 상태
        String state = "Y";

        Member member = loginRepository.findMember(name, email, state);
        log.info("member = {}", member);

        if (member != null) {
            String userId = member.getUserId();
            return userId;
        } else  {
            return null;
        }
    }

    /**
     * 이름과 핸드폰으로 아이디 찾기
     * @return
     */
    public String searchNameAndPhone(String name, String phone) {
        // 회원 상태
        String state = "Y";

        Member member = loginRepository.findMemberByPhone(name, phone, state);
        log.info("member = {}", member);

        if (member != null) {
            String userId = member.getUserId();
            return userId;
        } else  {
            return null;
        }
    }

    /**
     * 비밀번호 변경을 위한 이름과 아이디 일치하는지 확인
     * @return 결과 여부 (ok 혹은 null)
     */
    public String checkNameAndUserId(String name, String userId) {
        // 회원 상태
        String state = "Y";

        Member member = loginRepository.checkNameAndUserId(name, userId, state);
        log.info("member = {}", member);

        if (member != null) {
            return "ok";
        } else  {
            return null;
        }
    }

    /**
     * 비밀번호 변경
     * @param form 비밀번호 변경 화면
     */
    @Transactional
    public Member updateMember(UpdatePasswordForm form) {
        // unique한 userId로 조회
        Member findMember = loginRepository.findMemberById(form.getUserId());

        findMember.changePassword(form.getPassword());  // password 암호화 처리하기

        return findMember;
    }
}
