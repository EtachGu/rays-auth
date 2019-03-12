package com.auth.controller;

import com.auth.entity.OAuthClientDetails;
import com.auth.mapper.OAuthClientDetailsMapper;
import com.auth.service.ClientManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gu danpeng
 * @version 1.0
 * @date 2019-3-12
 */
@Controller
public class ClientController {

    @Autowired
    private ClientManageService clientManageService;

    @Autowired
    private OAuthClientDetailsMapper oAuthClientDetailsMapper;

    @GetMapping("/clients")
    public String clients(Model model,
                          @RequestParam(value = "clientId",required = false) String clientId){
        List<ClientDetails> clients =  clientManageService.getClientDetails();
        model.addAttribute("clients",clients);
        return "clients";
    }

    @GetMapping("/edit-client")
    public String editClient(Model model,
                             @RequestParam(value = "clientId",required = false) String clientId){
        OAuthClientDetails client = oAuthClientDetailsMapper.selectByPrimaryKey(clientId);

        List<String> grantTypes = Arrays.asList(client.getAuthorizedGrantTypes().split(","));
        List<String> redirectUris = Arrays.asList(client.getWebServerRedirectUri().split(","));

        model.addAttribute("client", client);
        model.addAttribute("grantTypes", grantTypes);
        model.addAttribute("redirectUris", redirectUris);
        return "edit-client";
    }

    @PostMapping("/edit-client/{clientId}")
    public void updateClient(@PathVariable String clientId, HttpServletRequest request){

    }
}
