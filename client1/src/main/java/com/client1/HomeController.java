package com.client1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

/**
 * @author: Gu danpeng
 * @date: 2018-12-22
 * @version：1.0
 */
@Controller
public class HomeController {

    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @RequestMapping("/home")
    @ResponseBody
    public String home() {
        return "This is Home";
    }

    @RequestMapping("/resource")
    @ResponseBody
    public String index(Model model, HttpServletResponse response) {
        model.addAttribute("name", "t");
        System.out.println("Current AccessToken: " + oAuth2RestTemplate.getAccessToken());
        ResponseEntity<String> responseEntity = oAuth2RestTemplate.getForEntity("http://127.0.0.1:8083/resource/test", String.class);
        return responseEntity.getBody();

    }
}
