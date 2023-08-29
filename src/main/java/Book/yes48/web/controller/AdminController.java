package Book.yes48.web.controller;

import Book.yes48.form.admin.AdminGoodsDto;
import Book.yes48.form.admin.AdminGoodsSaveForm;
import Book.yes48.form.admin.AdminGoodsUpdateForm;
import Book.yes48.form.admin.search.AdminGoodsSearch;
import Book.yes48.form.admin.search.SearchType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import Book.yes48.Entity.goods.Goods;
import Book.yes48.service.AdminService;
import Book.yes48.repository.admin.AdminRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired private final AdminRepository adminRepository;
    @Autowired private final AdminService adminService;

    @ModelAttribute("searchTypes")
    public SearchType[] searchType() {
        return SearchType.values();
    }

    /**
     * 등록된 상품 리스트
     */
    @GetMapping("/goodsList")
        public String goods(Model model, @ModelAttribute("adminGoodsSearch")AdminGoodsSearch adminGoodsSearch, Pageable pageable) {

        Page<AdminGoodsDto> goodsList = adminService.findList(pageable, adminGoodsSearch);
        model.addAttribute("goodsList", goodsList.getContent());

        log.info("adminGoodsSearch.searchBy={}", adminGoodsSearch.getSearchBy());
        log.info("adminGoodsSearch.searchType={}", adminGoodsSearch.getSearchType());

        int startPage = getStartPage(goodsList);

        model.addAttribute("startPage", getStartPage(goodsList));
        model.addAttribute("currentPage", goodsList.getNumber() + 1);
        model.addAttribute("endPage", getEndPage(goodsList, startPage));

        return "admin/goodsList";
    }

    // 페이지네이션
    private static int getStartPage(Page<AdminGoodsDto> goodsList) {
        return ((goodsList.getNumber() / 5) * 5) + 1;
    }

    private static int getEndPage(Page<AdminGoodsDto> goodList, int startPage) {
        if (goodList.getTotalPages() == 0) return 1;
        return Math.min(goodList.getTotalPages(), startPage + 4);
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
    public String saveGoods(@Validated @ModelAttribute("form") AdminGoodsSaveForm form, BindingResult bindingResult, MultipartFile file) throws IOException {

        //  파일 검증 로직
        if (file.isEmpty()) {
            bindingResult.addError(new ObjectError("form", "이미지는 필수입니다."));
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
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
    public String updateGoods(@Validated @ModelAttribute("form") AdminGoodsUpdateForm form, BindingResult bindingResult, MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "admin/saveGoods";
        }

        adminService.updateGoods(form.getId(), form, file);

        return "redirect:/admin/goodsList";
    }

}
