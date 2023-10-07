package book.yes48.web.controller;

import book.yes48.entity.order.OrderGoods;
import book.yes48.security.auth.PrincipleDetails;
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

    /**
     * order 화면 폼
     * @return
     */
    @GetMapping
    public String order(@AuthenticationPrincipal PrincipleDetails principleDetails,
                        Model model) {

        String userId = String.valueOf(principleDetails.getMember().getUserId());

        List<OrderGoods> findOrderList = orderService.getGoods(userId);
        MyPageInformationForm findMember = myPageService.findMemberById(userId);

        log.info("findMember.getBasicAddress() = {}", findMember.getBasicAddress());

        model.addAttribute("findOrderList", findOrderList);
        model.addAttribute("findMember", findMember);

        return "order/order";
    }

    /**
     * 주문 생성
     * @param address 배송 주소
     * @param orderPrice 주문 가격
     */
    @PostMapping("/createOrder")
    @ResponseBody
    public String order(@RequestParam("address") String address,
                        @RequestParam("orderPrice") String orderPrice,
                        @AuthenticationPrincipal PrincipleDetails principleDetails) {

        log.info("findMember.getAddress = {}", address);
        log.info("totalPrice = {}", orderPrice);

        String userId = String.valueOf(principleDetails.getMember().getUserId());
        String result = orderService.createOrder(orderPrice, address, userId);

        return result;

    }

    /**
     * 바로 구매 버튼 클릭시 주문 생성
     * @param count 갯수
     * @param goodsId 상품 아이디
     */
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

    /**
     * 결제 완료 화면
     * @return
     */
    @GetMapping("/completeOrder")
    public String completeOrder() {

        return "completeOrder";
    }
}
