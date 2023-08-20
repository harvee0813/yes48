package com.example.yes48.web.controller;

import com.example.yes48.Service.AdminService;
import com.example.yes48.Service.FileStoreService;
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
    @Autowired private final FileStoreService fileService;

    @GetMapping("/goods")
    public String product() {
        return "admin/goods";
    }

    @GetMapping("/saveGoods")
    public String saveGoods(Model model) { //@pathVariable Long itemId 추가하기

        model.addAttribute("form", new GoodsSaveForm());

        return "admin/saveGoods";
    }

    @PostMapping("/saveGoods")
    public String saveGoods(@ModelAttribute GoodsSaveForm form, BindingResult result, MultipartFile file) throws IOException {

        goodsService.saveGoods(form, file);

        return "redirect:/admin/goods";
    }

}
