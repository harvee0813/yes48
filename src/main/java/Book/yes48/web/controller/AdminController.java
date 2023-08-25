package Book.yes48.web.controller;

import Book.yes48.Entity.goods.form.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import Book.yes48.Entity.goods.Goods;
import Book.yes48.Service.AdminService;
import Book.yes48.repository.admin.AdminRepository;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired private final AdminRepository adminRepository;
    @Autowired private final AdminService adminService;

    /**
     * 등록된 상품 리스트
     */
    @GetMapping("/goodsList")
    public String goods(AdminGoodsSearchDto goodsSearchDto, AdminSearchCondition condition, Pageable pageable, Model model) {
        Page<AdminGoodsDto> goods = adminService.getAdminGoodsPage(goodsSearchDto, condition, pageable);

        model.addAttribute("goodsList", goods);
        model.addAttribute("AdminGoodsSearchDto", goodsSearchDto);
        model.addAttribute("maxPage", goods.getSize());

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

        //        if(file.isEmpty() && form.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//            return "item/itemForm";
//        }

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

        adminService.saveGoods(save);

        return "redirect:/admin/goodsList";
    }

    /**
     * 상품 상세 정보
     */
    @GetMapping("/{goodsId}/editGoods")
    public String detailGoods(@PathVariable Long goodsId, Model model) {
        AdminGoodsDto findGoods = adminRepository.getId(goodsId);
        log.info("AdminGoodsDto log={}", findGoods);

        model.addAttribute("form", findGoods);

        return "admin/editGoods";
    }


    @PostMapping("/{goodsId}/editGoods")
    public String updateGoods(@ModelAttribute AdminGoodsUpdateForm form, BindingResult result, MultipartFile file) throws IOException {

        adminService.updateGoods(form.getId(), form, file);

        return "redirect:/admin/goodsList";
    }

}
