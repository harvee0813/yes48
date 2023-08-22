package com.example.yes48.web.controller;

import com.example.yes48.Service.AdminService;
import com.example.yes48.Service.FileStoreService;
import com.example.yes48.domain.FileStore;
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
    @Autowired private final FileStoreService fileStoreService;

    /**
     * 등록된 상품 리스트 확인
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
    public String saveGoods(Model model) { //@pathVariable Long itemId 추가하기
        model.addAttribute("form", new AdminGoodsSaveForm());

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

        FileStore saveFile = fileStoreService.save(file);

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
                .fileStore(saveFile)
                .build();

        goodsService.saveGoods(save);

        return "redirect:/admin/goodsList";
    }

    /**
     * 상품 상세 정보
     */
    @GetMapping("/{goodsId}/editGoods")
    public String detailGoods(@PathVariable Long goodsId, Model model) {
        Goods findGoods = adminRepository.findOne(goodsId);

        AdminGoodsDto dto = AdminGoodsDto.builder()
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

        model.addAttribute("goods", findGoods);

        return "admin/goodsList";
    }

}
