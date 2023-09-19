package book.yes48.web.controller;

import book.yes48.security.auth.PrincipleDetails;
import book.yes48.service.MyCartService;
import book.yes48.web.form.cartItemDto.CartItemDto;
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
@RequestMapping("/myCart")
@RequiredArgsConstructor
public class MyCartController {

    @Autowired
    private final MyCartService myCartService;

    @GetMapping
    public String myCart(@AuthenticationPrincipal PrincipleDetails principleDetails,
                         Model model) {

        String userId = principleDetails.getMember().getUserId();
        List<CartItemDto> allCartItem = myCartService.findAllCartItem(userId);

        model.addAttribute("allCartItem", allCartItem);

        return "myCart";
    }

    @DeleteMapping("/delete/cartItem")
    @ResponseBody
    public String deleteItem(@RequestParam("goodsId") String goodsId,
                             @AuthenticationPrincipal PrincipleDetails principleDetails) {

        String userId = principleDetails.getMember().getUserId();
        String result = myCartService.deleteCartItem(goodsId, userId);

        return result;
    }

    @PostMapping("/updateQuantity")
    @ResponseBody
    public String updateQuantity(@RequestParam("goodsId") String goodsId,
                                 @RequestParam("quantity") String quantity,
                                 @AuthenticationPrincipal PrincipleDetails principleDetails) {

        String userId = principleDetails.getMember().getUserId();
        String result = myCartService.updateQuantity(goodsId, quantity, userId);

        return result;
    }

    /**
     * 도서 상세 상품 장바구니에 넣기
     *
     * @param count 수량
     * @return
     */
    @PostMapping("/CartItem")
    @ResponseBody
    public String nameCheck(@RequestParam("count") String count,
                            @RequestParam("goodsId") String goodsId,
                            @AuthenticationPrincipal PrincipleDetails principleDetails) {

        String userId = principleDetails.getMember().getUserId();

        String result = myCartService.setCartItem(goodsId, count, userId);

        return result;
    }
}
