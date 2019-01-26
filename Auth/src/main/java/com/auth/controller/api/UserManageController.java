package com.auth.controller.api;

import com.auth.entity.OAuthAccessToken;
import com.auth.entity.OAuthUser;
import com.auth.mapper.OAuthAccessTokenMapper;
import com.auth.mapper.OAuthUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */

@RequestMapping("/rest/users")
@RestController
public class UserManageController {

    @Autowired
    OAuthUserMapper oAuthUserMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ResponseBody
    @RequestMapping("me")
    public Principal getServiceUser(Principal principal) {
        System.out.println(principal);
        return principal;
    }

    @PostMapping
    public void createUser(@RequestParam("username") String username, @RequestParam("password") String password){
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setUserName(username);
        oAuthUser.setPassword(passwordEncoder.encode(password));
        oAuthUserMapper.insert(oAuthUser);
    }
}
