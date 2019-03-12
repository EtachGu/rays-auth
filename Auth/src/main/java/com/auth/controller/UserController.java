package com.auth.controller;

import com.auth.entity.OAuthUser;
import com.auth.mapper.OAuthUserMapper;
import com.auth.service.ClientManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Gu danpeng
 * @version 1.0
 * @date 2019-3-12
 */
@Controller
public class UserController {

    @Autowired
    OAuthUserMapper oAuthUserMapper;

    @GetMapping("/users")
    public String clients(Model model){
        List<OAuthUser> users =  oAuthUserMapper.selectAll();
        model.addAttribute("users",users);
        return "users";
    }
}
