package com.auth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String signup(){
        // Is already login in
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            return "redirect:";
        }
        return "join";
    }
}