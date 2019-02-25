package com.auth.controller;

import com.auth.entity.OAuthUser;
import com.auth.mapper.OAuthUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Gu danpeng
 * @date: 2019-1-22
 * @version：1.0
 */
@Controller
public class SignUpController {
    @Autowired
    private OAuthUserMapper oAuthUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/sign_up")
    public String createUser(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response){
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setUserName(username);
        oAuthUser.setPassword(passwordEncoder.encode(password));

        if(oAuthUserMapper.existsWithPrimaryKey(username)){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "register";
        } else {
            oAuthUserMapper.insert(oAuthUser);
        }

        // 自动登录
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser=authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return "redirect:";

    }

    @GetMapping("/register")
    public String register(){
        // Is already login
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            return "redirect:";
        }
        return "register";
    }
}
