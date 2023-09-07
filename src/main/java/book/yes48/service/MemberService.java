package book.yes48.service;

import book.yes48.entity.member.Member;
import book.yes48.form.member.MemberSaveForm;
import book.yes48.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    /**
     * 회원 등록
     * @param form view에서 가져온 회원 등록 정보
     * @return 등록 id
     */
    @Transactional
    public Long save(MemberSaveForm form) {

        Member member = Member.builder()
                .userId(form.getUserId())
                .password(form.getPassword())
                .name(form.getName())
                .email(form.getEmail())
                .phone(form.getPhone())
                .postcode(form.getPostcode())
                .address(form.getAddress())
                .detailsAddress(form.getDetailsAddress())
                .extraAddress(form.getExtraAddress())
                .state("Y")
                .authority("client")
                .build();

        Long saveId = memberRepository.save(member).getId();

        return saveId;
    }

    /**
     * 아이디 중복 체크
     * @param userId 중복 체크할 아이디
     * @return 중복 여부
     */
    public String userIdCheck(String userId) {
        Member member = memberRepository.findUser(userId);
        if (member != null) {
            return null;
        } else {
            return "ok";
        }
    }

    /**
     * 아이디로 회원 찾기
     * @param id 찾을 회원의 id
     * @return id로 찾은 member 반환
     */
    public Optional<Member> findById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member;
    }

}
