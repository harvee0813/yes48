package book.yes48.web.controller;

import book.yes48.form.goods.GoodsDetailDto;
import book.yes48.form.goods.GoodsDto;
import book.yes48.form.goods.GoodsSearch;
import book.yes48.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    /**
     * 국내 도서 리스트
     * @return
     */
    @GetMapping("/book/domesticBooks")
    public String domesticBooks(GoodsSearch goodsSearch, @PageableDefault(size = 8) Pageable pageable, Model model) {

        Page<GoodsDto> domesticBooks = goodsService.domesticBooksFindList(goodsSearch, pageable);
        log.info("domesticBooks = {}", domesticBooks.getSize());

        int startPage = getStartPage(domesticBooks);

        model.addAttribute("books", domesticBooks.getContent());
        model.addAttribute("startPage", startPage);
        model.addAttribute("currentPage", domesticBooks.getNumber() + 1);
        model.addAttribute("endPage", getEndPage(domesticBooks, startPage));

        return "goods/book/domesticBooks";
    }

    /**
     * 외국 도서 리스트
     * @return
     */
    @GetMapping("/book/foreignBooks")
    public String foreignBooks(GoodsSearch goodsSearch, @PageableDefault(size = 8) Pageable pageable, Model model) {

        Page<GoodsDto> foreignBooks = goodsService.foreignBooksFindList(goodsSearch, pageable);
        log.info("foreignBooks = {}", foreignBooks.getSize());

        int startPage = getStartPage(foreignBooks);

        model.addAttribute("books", foreignBooks.getContent());
        model.addAttribute("startPage", startPage);
        model.addAttribute("currentPage", foreignBooks.getNumber() + 1);
        model.addAttribute("endPage", getEndPage(foreignBooks, startPage));

        return "goods/book/foreignBooks";
    }

    /**
     * 도서 상세 정보
     * @return
     */
    @GetMapping("/book/{goodsId}/detailBook")
    public String detailBook(@PathVariable Long goodsId, Model model) {
        GoodsDetailDto findBook = goodsService.getId(goodsId);
        log.info("findBook = {}", findBook);

        model.addAttribute("book", findBook);

        return "goods/book/detailBook";
    }

    /**
     * 음반 리스트
     * @return
     */
    @GetMapping("/music")
    public String music(GoodsSearch goodsSearch, @PageableDefault(size = 8) Pageable pageable, Model model) {

        Page<GoodsDto> musics = goodsService.musicFindList(goodsSearch, pageable);
        log.info("musics = {}", musics.getSize());

        int startPage = getStartPage(musics);

        model.addAttribute("musics", musics.getContent());
        model.addAttribute("startPage", startPage);
        model.addAttribute("currentPage", musics.getNumber() + 1);
        model.addAttribute("endPage", getEndPage(musics, startPage));

        return "goods/music/music";
    }

    /**
     * 음반 상세 정보 화면
     * @return
     */
    @GetMapping("music/{goodsId}/detailMusic")
    public String detailMusic(@PathVariable Long goodsId, Model model) {
        GoodsDetailDto findMusic = goodsService.getId(goodsId);
        log.info("findMusic = {}", findMusic);

        model.addAttribute("music", findMusic);

        return "goods/music/detailMusic";
    }

    /**
     * 검색된 상품 리스트
     * @return
     */
    @GetMapping("/searchGoods")
    public String searchGoods() {
        return "goods/searchGoods";
    }

    // 페이지네이션 - 첫 페이지 구하기
    private static int getStartPage(Page<GoodsDto> books) {
        return ((books.getNumber() / 5) * 5) + 1;
    }

    // 페이지네이션 - 마지막 페이지 구하기
    private static int getEndPage(Page<GoodsDto> books, int startPage) {
        if (books.getTotalPages() == 0) return 1;
        return Math.min(books.getTotalPages(), startPage + 4);
    }
}
