package book.yes48.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    /**
     * 국내 도서 리스트
     * @return
     */
    @GetMapping("/book/domesticBooks")
    public String domesticBooks() {
        return "goods/book/domesticBooks";
    }

    /**
     * 외국 도서 리스트
     * @return
     */
    @GetMapping("/book/foreignBooks")
    public String foreignBooks() {
        return "goods/book/foreignBooks";
    }

    /**
     * 도서 상세 정보
     * @return
     */
    @GetMapping("/book/detailBook")
    public String detailBook() {
        return "goods/book/detailBook";
    }

    /**
     * 음반 리스트
     * @return
     */
    @GetMapping("/music")
    public String music() {
        return "goods/music/music";
    }

    /**
     * 음반 상세 정보 폼
     * @return
     */
    @GetMapping("/music/detailMusic")
    public String detailMusic() {
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
}
