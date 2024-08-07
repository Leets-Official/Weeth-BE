package leets.weeth.global.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ThymeLeafController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    @GetMapping("/member")
    public String member(Model model) {
        model.addAttribute("pageTitle", "멤버 관리");
        return "member";
    }

    @GetMapping("/meeting")
    public String attendance(Model model) {
        model.addAttribute("pageTitle", "출석 관리");
        return "meeting";
    }


    @GetMapping("/penalty")
    public String penalty(Model model) {
        model.addAttribute("pageTitle", "패널티 관리");
        return "penalty";
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("pageTitle", "회비");
        return "account";
    }
}