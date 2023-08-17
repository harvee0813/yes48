package com.example.yes48.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/goods")
    public String product() {
        return "admin/goods";
    }

    @GetMapping("/admin/addGoods")
    public String addGoods() {
        return "admin/addGoods";
    }
}
