package com.auth.controller;

import com.auth.entity.OAuthAccessToken;
import com.auth.service.TokenManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gu danpeng
 * @version 1.0
 * @date 2019-3-13
 */
@PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
@Controller
public class TokenController {

    @Autowired
    private TokenManageService tokenManageService;

    @Resource(name = "defaultTokenServices")
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("/tokens")
    public String tokenPage(Model model){
        List<OAuthAccessToken> tokens = tokenManageService.getDetailAccessTokens();

        model.addAttribute("tokens",tokens);
        return "tokens";
    }

    @GetMapping("/edit-token")
    public String editToken(@RequestParam String authenticationId, Model model){
        OAuthAccessToken token = tokenManageService.getDetailAccessToken(authenticationId);
        boolean errorInfo = true;
        if(token != null){
            model.addAttribute("token",token);
            errorInfo = false;
        }
        model.addAttribute("errorInfo", errorInfo);
        return "edit-token";
    }

    @GetMapping("/revoke-token")
    public String revokeToken(@RequestParam String token){
        consumerTokenServices.revokeToken(token);
        return "redirect:tokens";
    }
}
