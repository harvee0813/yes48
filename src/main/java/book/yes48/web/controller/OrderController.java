package book.yes48.web.controller;

import book.yes48.entity.member.Member;
import book.yes48.entity.order.OrderGoods;
import book.yes48.repository.member.MemberRepository;
import book.yes48.security.auth.PrincipleDetails;
import book.yes48.service.MemberService;
import book.yes48.service.MyPageService;
import book.yes48.service.OrderService;
import book.yes48.web.form.myPage.MyPageInformationForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private final OrderService orderService;
    @Autowired
    private final MyPageService myPageService;

    @GetMapping
    public String order(@AuthenticationPrincipal PrincipleDetails principleDetails,
                        Model model) {

        String userId = String.valueOf(principleDetails.getMember().getUserId());

        List<OrderGoods> findOrderList = orderService.getGoods(userId);
        MyPageInformationForm findMember = myPageService.findMemberById(userId);

        model.addAttribute("findOrderList", findOrderList);
        model.addAttribute("findMember", findMember);

        return "order/order";
    }

    @PostMapping("/OrderItem")
    @ResponseBody
    public String setOrderGoods(@RequestParam("count") String count,
                                @RequestParam("goodsId") String goodsId,
                                @AuthenticationPrincipal PrincipleDetails principleDetails) {

        String userId = String.valueOf(principleDetails.getMember().getUserId());
        String memberPkId = String.valueOf(principleDetails.getMember().getId());

        String result = orderService.GoodsBuyNow(count, goodsId, userId, memberPkId);

        return result;
    }
}
