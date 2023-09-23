package book.yes48.web.controller;

import book.yes48.entity.order.OrderGoods;
import book.yes48.security.auth.PrincipleDetails;
import book.yes48.service.MyPageService;
import book.yes48.service.OrderService;
import book.yes48.web.form.OrderHistoryDto;
import book.yes48.web.form.myPage.AddressUpdateForm;
import book.yes48.web.form.myPage.MyPageInformationForm;
import book.yes48.web.form.myPage.PhoneUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/myPage")
public class MyPageController {

    @Autowired
    private final MyPageService myPageService;
    @Autowired
    private final OrderService orderService;

    /**
     * 주문 목록
     * @return
     */
    @GetMapping("/orderHistoryList")
    public String orderHistoryList(@PageableDefault(size = 5) Pageable pageable,
                                   @AuthenticationPrincipal PrincipleDetails principleDetails,
                                   Model model) {

        // 주문 목록 조회
        Long memberPkId = principleDetails.getMember().getId();
        Page<OrderHistoryDto> orderList = orderService.getOrderList(pageable, memberPkId);
        model.addAttribute("orderList", orderList);

        // 페이징
        int startPage = getStartPage(orderList);

        model.addAttribute("startPage", getStartPage(orderList));
        model.addAttribute("currentPage", orderList.getNumber() + 1);
        model.addAttribute("endPage", getEndPage(orderList, startPage));

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

    // 페이지네이션 - 첫 페이지 구하기
    private static int getStartPage(Page<OrderHistoryDto> goodsList) {
        return ((goodsList.getNumber() / 5) * 5) + 1;
    }

    // 페이지네이션 - 마지막 페이지 구하기
    private static int getEndPage(Page<OrderHistoryDto> goodList, int startPage) {
        if (goodList.getTotalPages() == 0) return 1;
        return Math.min(goodList.getTotalPages(), startPage + 4);
    }
}
