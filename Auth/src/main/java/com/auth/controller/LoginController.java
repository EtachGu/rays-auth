package com.auth.controller;

import com.auth.service.LoginAttemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */
@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginAttemptService loginAttemptService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String errorParam, Model model, HttpServletRequest request) {
        if(errorParam != null){
            model.addAttribute("loginError", true);
            if(loginAttemptService.isBlocked(getClientIP(request))){
                logger.warn("Too many failed login attempts from your ip address" + getClientIP(request));
                model.addAttribute("errorInfo","Sorry, too many failed login attempts, please try again later");
            }
        }
        return "login";
    }



    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}