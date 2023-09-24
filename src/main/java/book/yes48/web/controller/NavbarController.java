package book.yes48.web.controller;

import book.yes48.web.form.NavbarDto;
import book.yes48.service.NavbarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NavbarController {
    @Autowired
    private final NavbarService navbarService;

    /**
     * 네비바 검색 창
     * @param search 검색어
     * @param pageable 페이징
     */
    @GetMapping("/searchGoods")
    public String searchForm(@RequestParam String search, @PageableDefault(size = 8) Pageable pageable, Model model) {

        if (search == null) {
            return "/";
        }

        Page<NavbarDto> searchGoods = navbarService.goodsSearchList(pageable, search);
        log.info("searchGoods = {}", searchGoods);

        int startPage = getStartPage(searchGoods);

        model.addAttribute("startPage", getStartPage(searchGoods));
        model.addAttribute("currentPage", searchGoods.getNumber() + 1);
        model.addAttribute("endPage", getEndPage(searchGoods, startPage));
        model.addAttribute("searchGoods", searchGoods);

        return "/goods/searchGoods";
    }

    // 페이지네이션 - 첫 페이지 구하기
    private static int getStartPage(Page<NavbarDto> goodsList) {
        return ((goodsList.getNumber() / 5) * 5) + 1;
    }

    // 페이지네이션 - 마지막 페이지 구하기
    private static int getEndPage(Page<NavbarDto> goodList, int startPage) {
        if (goodList.getTotalPages() == 0) return 1;
        return Math.min(goodList.getTotalPages(), startPage + 4);
    }
}
