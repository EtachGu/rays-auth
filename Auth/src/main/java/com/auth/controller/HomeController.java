package com.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Gu danpeng
 * @version 1.0
 * @date 2019-3-11
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
