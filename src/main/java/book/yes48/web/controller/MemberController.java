package book.yes48.web.controller;

import book.yes48.web.form.member.MemberSaveForm;
import book.yes48.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/join")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @GetMapping
    public String join(Model model) {

        MemberSaveForm form = MemberSaveForm.builder().build();
        model.addAttribute("form",  form);

        return "member/join";
    }

    @PostMapping
    public String join(@Validated @ModelAttribute("form") MemberSaveForm form,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "member/join";
        }

        memberService.save(form);

        return "redirect:/join/completeJoin";
    }

    /**
     * 아이디 중복 체크
     * @param userId 아이디
     */
    @PostMapping("/userIdCheck")
    @ResponseBody
    public String nameCheck(@RequestParam("userId") String userId) {
        log.info("userId = {}", userId);

        String checkResult = memberService.userIdCheck(userId);

        return checkResult;
    }

    /**
     * 회원 가입 성공 화면
     * @return
     */
    @GetMapping("/completeJoin")
    public String completeJoin() {
        
        return "member/completeJoin";
    }
}
