package book.yes48.web.controller;

import book.yes48.repository.member.MemberRepository;
import book.yes48.security.auth.PrincipleDetails;
import book.yes48.service.MyPageService;
import book.yes48.web.form.myPage.AddressUpdateForm;
import book.yes48.web.form.myPage.MyPageInformationForm;
import book.yes48.web.form.myPage.PhoneUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/myPage")
public class MyPageController {

    @Autowired MyPageService myPageService;

    /**
     * 주문 목록
     * @return
     */
    @GetMapping("/orderHistoryList")
    public String orderHistoryList() {
        return "/member/myPage/orderHistoryList";
    }

    /**
     * 내정보 보기 화면
     * @param principleDetails 로그인한 사용자의 정보
     */
    @GetMapping("/information")
    public String information(@AuthenticationPrincipal PrincipleDetails principleDetails, Model model) {

        // 로그인한 아이디로 고객의 정보를 찾는다.
        MyPageInformationForm form = myPageService.findMemberById(principleDetails.getMember().getUserId());

        model.addAttribute("form", form);

        return "/member/myPage/information";
    }

    /**
     * 내정보 수정 화면
     * @param principleDetails 로그인한 사용자의 정보
     * @return
     */
    @GetMapping("/updateInformation")
    public String updateInformation(@AuthenticationPrincipal PrincipleDetails principleDetails, Model model) {

        MyPageInformationForm form = myPageService.findMemberById(principleDetails.getMember().getUserId());

        PhoneUpdateForm phoneUpdateForm = PhoneUpdateForm.builder().build();
        AddressUpdateForm addressUpdateForm = AddressUpdateForm.builder().build();

        model.addAttribute("form", form);
        model.addAttribute("phoneUpdateForm", phoneUpdateForm);
        model.addAttribute("addressUpdateForm", addressUpdateForm);

        return "/member/myPage/updateInformation";
    }

    /**
     * 내 정보 수정 폼
     * @param principleDetails 로그인한 사용자의 정보
     * @param phoneUpdateForm 핸드폰 번호 수정 폼
     * @param addressUpdateForm 배송 주소 번호 수정 폼
     * @return
     */
    @PostMapping("/updateInformation")
    public String updateInformation(@AuthenticationPrincipal PrincipleDetails principleDetails,
                                    @ModelAttribute("phoneUpdateForm") PhoneUpdateForm phoneUpdateForm,
                                    @ModelAttribute("addressUpdateForm") AddressUpdateForm addressUpdateForm) {

        myPageService.updateAddressAndPhone(principleDetails.getMember().getUserId(), phoneUpdateForm, addressUpdateForm);

        return "redirect:/myPage/updateInformation";
    }

    /**
     * 회원 탈퇴 화면
     * @return
     */
    @GetMapping("/withdraw")
    public String withdraw() {

        return "/member/myPage/withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@AuthenticationPrincipal PrincipleDetails principleDetails) {
        String userId = principleDetails.getMember().getUserId();

        myPageService.updateState(userId);

        return "redirect:/";
    }
}
