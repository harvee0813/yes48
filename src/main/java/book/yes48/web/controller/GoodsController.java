package book.yes48.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoodsController {

    @GetMapping("/book-list")
    public String list() {
        return "book/list";
    }

    @GetMapping("/goods")
    public String detail() {
        return "book/goods";
    }
}
