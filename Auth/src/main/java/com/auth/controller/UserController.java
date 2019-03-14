package com.auth.controller;

import com.auth.entity.OAuthUser;
import com.auth.mapper.OAuthUserMapper;
import com.auth.service.ClientManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gu danpeng
 * @version 1.0
 * @date 2019-3-12
 */
@Controller
public class UserController {

    @Autowired
    OAuthUserMapper oAuthUserMapper;

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/users")
    public String clients(Model model){
        List<OAuthUser> users =  oAuthUserMapper.selectAll();
        model.addAttribute("users",users);
        model.addAttribute("loggedUsers", listLoggedInUserNames());
        return "users";
    }

    public List<String> listLoggedInUserNames() {
        final List<String> allPrincipals = sessionRegistry.getAllPrincipals()
                .stream()
                .map(e -> ((User) e).getUsername())
                .collect(Collectors.toList());
        return allPrincipals;
    }
}
