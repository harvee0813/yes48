package com.example.yes48.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/order/myCart")
    public String myCart() {
        return "order/myCart";
    }

    @GetMapping("/order")
    public String order() {
        return "order/order";
    }
}
