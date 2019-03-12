package com.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String errorParam, Model model) {
        if(errorParam != null){
            model.addAttribute("loginError", true);
        }
        return "login";
    }
}