package book.yes48.web.controller;

import book.yes48.entity.FileStore;
import book.yes48.form.admin.AdminGoodsDto;
import book.yes48.form.admin.AdminGoodsSaveForm;
import book.yes48.form.admin.AdminGoodsUpdateForm;
import book.yes48.form.admin.search.AdminGoodsSearch;
import book.yes48.form.admin.search.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import book.yes48.service.AdminService;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired private final AdminService adminService;

    @ModelAttribute("searchTypes")
    public SearchType[] searchType() {
        return SearchType.values();
    }

    /**
     * 상품 목록 폼
     */
    @GetMapping("/goodsList")
        public String goods(@ModelAttribute("adminGoodsSearch") AdminGoodsSearch adminGoodsSearch,
                            Pageable pageable, Model model) {

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

    /**
     * 상품 등록 폼
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
    public String saveGoods(@Validated @ModelAttribute("form") AdminGoodsSaveForm form,
                            BindingResult bindingResult, MultipartFile file) throws IOException {

        //  파일 검증 로직
        if (file.isEmpty()) {
            bindingResult.addError(new ObjectError("form", "이미지는 필수입니다."));
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "admin/saveGoods";
        }

        adminService.saveGoods(form, file);

        return "redirect:/admin/goodsList";
    }

    /**
     * 상품 상세 정보 폼
     */
    @GetMapping("/{goodsId}/editGoods")
    public String detailGoods(@PathVariable Long goodsId, Model model) {
        AdminGoodsDto findGoods = adminService.getId(goodsId);
        log.info("AdminGoodsDto = {}", findGoods);

        model.addAttribute("form", findGoods);

        return "admin/editGoods";
    }

    /**
     * 상품 상세 정보 수정
     */
    @PostMapping("/{goodsId}/editGoods")
    public String updateGoods(@Validated @ModelAttribute("form") AdminGoodsUpdateForm form,
                              BindingResult bindingResult, MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);

            // form에 fileStore를 넣어주기 위한 코드 -> editGoods에서 이미지가 보여진다.
            AdminGoodsDto getFilenameByGoodsId = adminService.getId(form.getId());
            form.setFileStore(getFilenameByGoodsId.getFileStore());
            return "admin/editGoods";
        }

        adminService.updateGoods(form.getId(), form, file);


        return "redirect:/admin/goodsList";
    }

    /**
     * 등록시 상품 이름 중복 확인
     * @param name 상품 이름
     */
    @PostMapping("/goodsNameCheck")
    @ResponseBody
    public String nameCheck(@RequestParam("name") String name) {
        log.info("goodsName = {}", name);
        String checkResult = adminService.nameCheck(name);
        return checkResult;
    }

    // 페이지네이션 - 첫 페이지 구하기
    private static int getStartPage(Page<AdminGoodsDto> goodsList) {
        return ((goodsList.getNumber() / 5) * 5) + 1;
    }

    // 페이지네이션 - 마지막 페이지 구하기
    private static int getEndPage(Page<AdminGoodsDto> goodList, int startPage) {
        if (goodList.getTotalPages() == 0) return 1;
        return Math.min(goodList.getTotalPages(), startPage + 4);
    }
}
