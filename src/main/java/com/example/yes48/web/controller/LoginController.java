package com.example.yes48.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/login/findId")
    public String findId() {
        return "login/find-id";
    }

    @GetMapping("/login/findPassword")
    public String findPassword() {
        return "login/find-password";
    }
}
