package book.yes48.web.controller;

import book.yes48.security.auth.PrincipleDetails;
import book.yes48.web.form.goods.GoodsDto;
import book.yes48.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    private final HomeService homeService;

    /**
     * 메인 화면
     * @param model 책 & 음반 상품을 담는 model
     */
    @GetMapping("/")
    public String home(Model model) {
        // 편집장의 추천 도서 4권
        List<GoodsDto> fourSuggestionBooks = homeService.selectFourSuggestionBooks();
        log.info("selectFourSuggestionBooks = {}", fourSuggestionBooks);

        // 최신 도서 4권
        List<GoodsDto> fourNewBooks = homeService.selectFourNewBooks();
        log.info("fourNewBooks = {}", fourNewBooks);

        // 최신 음반 3장
        List<GoodsDto> threeMusic = homeService.selectThreeMusic();
        log.info("fourMusic = {}", threeMusic);

        model.addAttribute("fourSuggestionBooks", fourSuggestionBooks);
        model.addAttribute("fourNewBooks", fourNewBooks);
        model.addAttribute("threeMusic", threeMusic);

        return "home";
    }
}
