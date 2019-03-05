package com.auth.controller.api;

import com.auth.entity.OAuthClientDetails;
import com.auth.mapper.OAuthClientDetailsMapper;
import com.auth.service.ClientManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Gu danpeng
 * @date: 2019-1-15
 * @version：1.0
 */
@RestController
@RequestMapping("/rest/clients")
public class ClientManageController {

    @Autowired
    private ClientManageService clientManageService;

    @Autowired
    private OAuthClientDetailsMapper oAuthClientDetailsMapper;

    @GetMapping("/all")
    public List<ClientDetails> getClientDetails(){
        return clientManageService.getClientDetails();
    }

    @PostMapping("")
    public void createClientDetails(@RequestBody OAuthClientDetails oAuthClientDetails){
        oAuthClientDetailsMapper.insert(oAuthClientDetails);
//        clientManageService.addClientDetails(oAuthClientDetails);
    }

}
