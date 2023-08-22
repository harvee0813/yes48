package com.example.yes48.web.controller;

import com.example.yes48.Service.AdminService;
import com.example.yes48.domain.goods.Goods;
import com.example.yes48.domain.goods.admin.AdminGoodsSaveForm;
import com.example.yes48.domain.goods.admin.AdminGoodsDto;
import com.example.yes48.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired private final AdminRepository adminRepository;
    @Autowired private final AdminService goodsService;

    /**
     * 등록된 상품 리스트
     */
    @GetMapping("/goodsList")
    public String product(Model model) {
        List<AdminGoodsDto> goods = adminRepository.findAll();
        model.addAttribute("goodsList", goods);
        return "admin/goodsList";
    }

    /**
     * 상품 등록 폼 이동
     */
    @GetMapping("/saveGoods")
    public String saveGoods(Model model) {

        AdminGoodsSaveForm form = AdminGoodsSaveForm.builder().build();

        model.addAttribute("form", form);

        return "admin/saveGoods";
    }

    /**
     * 상품 등록
     */
    @PostMapping("/saveGoods")
    public String saveGoods(@ModelAttribute AdminGoodsSaveForm form, BindingResult result, MultipartFile file) throws IOException {

        if (result.hasErrors()) {
            return "admin/saveGoods";
        }

        Goods save = Goods.builder()
                .name(form.getName())
                .sort(form.getSort())
                .author(form.getAuthor())
                .publisher(form.getPublisher())
                .publisherDate(form.getPublisherDate())
                .price(form.getPrice())
                .stockQuantity(form.getStockQuantity())
                .event(form.getEvent())
                .state(form.getState())
                .fileStore(form.file(file))
                .build();

        goodsService.saveGoods(save);

        return "redirect:/admin/goodsList";
    }

    /**
     * 상품 상세 정보
     */
    @GetMapping("/{goodsId}/edit")
    public String detailGoods(@PathVariable Long goodsId, Model model) {
        Goods findGoods = adminRepository.findOne(goodsId);

        AdminGoodsDto dtoForm = AdminGoodsDto.builder()
                .id(findGoods.getId())
                .name(findGoods.getName())
                .sort(findGoods.getSort())
                .author(findGoods.getAuthor())
                .publisher(findGoods.getPublisher())
                .publisherDate(findGoods.getPublisherDate())
                .price(findGoods.getPrice())
                .stockQuantity(findGoods.getStockQuantity())
                .event(findGoods.getEvent())
                .state(findGoods.getState())
                .fileStore(findGoods.getFileStore())
                .build();

        model.addAttribute("form", dtoForm);

        return "admin/editGoods";
    }

}
