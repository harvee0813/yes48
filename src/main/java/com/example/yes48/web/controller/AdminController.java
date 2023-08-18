package com.example.yes48.web.controller;

import com.example.yes48.Service.AdminService;
import com.example.yes48.domain.goods.Goods;
import com.example.yes48.domain.goods.GoodsSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired private final AdminService goodsService;

    @GetMapping("/goods")
    public String product() {
        return "admin/goods";
    }

    @GetMapping("/addGoods")
    public String saveGoods(Model model) { //@pathVariable Long itemId

        model.addAttribute("form", new GoodsSaveForm());

        return "admin/addGoods";
    }

    @PostMapping("/addGoods")
    public String saveGoods(@ModelAttribute GoodsSaveForm form, BindingResult result, MultipartFile file) throws IOException {

        goodsService.addGoods(form, file);

        return "redirect:/admin/goods";
    }

}
