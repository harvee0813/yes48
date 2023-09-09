package book.yes48.web.controller;

import book.yes48.web.form.login.UpdatePasswordForm;
import book.yes48.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private final LoginService loginService;

    /**
     * 로그인 화면
     */
    @GetMapping
    public String login() {
        return "login/login";
    }

    /**
     * 아이디 찾기 변경 화면
     */
    @GetMapping("/findId")
    public String findId() {
        return "login/find-id";
    }

    /**
     * 이름과 이메일로 id 찾기
     * @return 찾은 id
     */
    @PostMapping("/findId/searchNameAndEmail")
    @ResponseBody
    public String searchNameAndEmail(@RequestParam("name") String name,
                                     @RequestParam("email") String email) {
        log.info("name = {}", name);
        log.info("email = {}", email);

        String userId = loginService.searchNameAndEmail(name, email);
        log.info("userId = {}", userId);

        return userId;
    }

    /**
     * 이름과 핸드폰 번호로 id 찾기
     * @return 찾은 id
     */
    @PostMapping("/findId/searchNameAndPhone")
    @ResponseBody
    public String searchNameAndPhone(@RequestParam("name") String name,
                                     @RequestParam("phone") String phone) {
        log.info("name = {}", name);
        log.info("email = {}", phone);

        String userId = loginService.searchNameAndPhone(name, phone);
        log.info("userId = {}", userId);

        return userId;
    }

    /**
     * 비밀번호 변경 화면
     */
    @GetMapping("/findPassword")
    public String findPassword(Model model) {
        UpdatePasswordForm form = UpdatePasswordForm.builder().build();

        model.addAttribute("form", form);

        return "login/find-password";
    }

    /**
     * 비밀번호 변경
     * @param form 비밀번호 변경 화면
     */
    @PostMapping("/findPassword")
    public String changePassword(@ModelAttribute("form") UpdatePasswordForm form, BindingResult bindingResult) {
        log.info("form.getUserId = {}", form.getUserId());
        log.info("form.getPassword = {}", form.getPassword());

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);

            return "login/password";
        }

        loginService.updateMember(form);

        return "redirect:/login/completePassword";
    }

    /**
     * 비밀번호 변경을 위한 이름과 아이디 일치하는지 확인
     * @return 결과 여부 (ok 혹은 null)
     */
    @PostMapping("/findPassword/checkNameAndUserId")
    @ResponseBody
    public String checkNameAndId(@RequestParam("name") String name,
                                 @RequestParam("userId") String userId) {
        log.info("name = {}", name);
        log.info("email = {}", userId);

        String result = loginService.checkNameAndUserId(name, userId);
        log.info("result = {}", result);

        return result;
    }

    /**
     * 비밀번호 변경 완료 페이지
     * @return
     */
    @GetMapping("/completePassword")
    public String completeChangePassword() {
        return "login/completePassword";
    }
}
