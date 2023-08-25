package Book.yes48.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login() {
        return "login/login";
    }

    @GetMapping("/findId")
    public String findId() {
        return "login/find-id";
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return "login/find-password";
    }
}
