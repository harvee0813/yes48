package com.example.yes48.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/myPage/orderHistoryList")
    public String orderHistoryList() {
        return "/member/myPage/orderHistoryList";
    }

    @GetMapping("/myPage/information")
    public String information() {
        return "/member/myPage/information";
    }
}
