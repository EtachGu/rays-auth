package com.auth.controller;

import com.auth.entity.OAuthAccessToken;
import com.auth.mapper.OAuthAccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    OAuthAccessTokenMapper oauthAccessTokenMapper;

    @ResponseBody
    @RequestMapping("me")
    public Principal getPhotoServiceUser(Principal principal) {
        System.out.println(principal);
        return principal;
    }

    @GetMapping("/token")
    @ResponseBody
    public List<OAuthAccessToken> getTest(){
        return oauthAccessTokenMapper.selectAll();
    }
}
