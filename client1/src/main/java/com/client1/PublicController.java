package com.client1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Gu danpeng
 * @date: 2019-1-28
 * @version：1.0
 */
@Controller
@RequestMapping("/api")
public class PublicController {

    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @Qualifier("credentialTemplate")
    @Autowired
    OAuth2RestTemplate oAuth2CredRestTemplate;

    @GetMapping("/tokens")
    @ResponseBody
    public String listTokens(){
        ResponseEntity<String> responseEntity = oAuth2CredRestTemplate.getForEntity("http://127.0.0.1:8080/authserver/rest/access_tokens", String.class);
        return responseEntity.getBody();
    }
}
