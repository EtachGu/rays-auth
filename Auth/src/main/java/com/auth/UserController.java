package com.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */

@RequestMapping("/user")
@RestController
public class UserController {

    @ResponseBody
    @RequestMapping("me")
    public Principal getPhotoServiceUser(Principal principal) {
        System.out.println(principal);
        return principal;
    }
}
