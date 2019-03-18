package com.auth.controller;

import com.auth.service.LoginAttemptService;
import com.auth.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Gu danpeng
 * @version 1.0
 * @date 2019-3-11
 */
@Controller
public class HomeController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @GetMapping("/home")
    public String home(Model model){
        List<Object> loggedInUsers = sessionRegistry.getAllPrincipals();
        model.addAttribute("loggedInCount", loggedInUsers.size());
        model.addAttribute("loginIP",loginAttemptService.getLoginIPMap());
        return "home";
    }
}
