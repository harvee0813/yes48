package book.yes48.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @GetMapping
    public String order() {
        return "order/order";
    }

    @GetMapping("/myCart")
    public String myCart() {
        return "order/myCart";
    }
}
