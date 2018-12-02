package com.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */

@RestController
public class LoginController {
    @GetMapping("/")
    public ModelAndView require() {
        return new ModelAndView("/login");
    }
}