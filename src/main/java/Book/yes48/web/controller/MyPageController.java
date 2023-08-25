package Book.yes48.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myPage")
public class MyPageController {

    @GetMapping("/orderHistoryList")
    public String orderHistoryList() {
        return "/member/myPage/orderHistoryList";
    }

    @GetMapping("/information")
    public String information() {
        return "/member/myPage/information";
    }

    @GetMapping("/withdraw")
    public String withdraw() {
        return "/member/myPage/withdraw";
    }
}
