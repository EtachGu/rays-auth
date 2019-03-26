package com.auth.controller.api;

import com.auth.entity.OAuthAccessToken;
import com.auth.service.TokenManageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Gu danpeng
 * @date: 2019-1-4
 * @version：1.0
 */
@RequestMapping("/rest")
@RestController
public class TokenManageController {

    @Autowired
    private TokenManageService tokenManageService;

    @Resource(name = "tokenStore")
    private TokenStore tokenStore;

    @Resource(name = "defaultTokenServices")
    private DefaultTokenServices consumerTokenServices;


    @Cacheable("access_tokens")
    @GetMapping("/access-tokens")
    public List<?> getOAuth2AccessToken(@RequestParam(value = "showDetails", required = false, defaultValue = "false") boolean showDetails){

        if(!showDetails){
            List<OAuthAccessToken> listAccessToken = tokenManageService.getAccessTokens();
            return listAccessToken.stream()
                    .map(e -> (OAuth2AccessToken) SerializationUtils.deserialize(e.getToken()))
                    .collect(Collectors.toList());
        }

        return tokenManageService.getDetailAccessTokens();
    }


    @PostMapping("/revoke-token/{token}")
    @ResponseBody
    public void revokeToken(HttpServletRequest request, @PathVariable String token) {
        consumerTokenServices.revokeToken(token);
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/tokens")
//    @ResponseBody
//    public List<String> getTokens() {
//        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId("sampleClientId");
//        return Optional.ofNullable(tokens).orElse(Collections.emptyList()).stream().map(OAuth2AccessToken::getValue).collect(Collectors.toList());
//    }

    @PostMapping("/revoke-refresh-token/{token:.*}")
    @ResponseBody
    public String revokeRefreshToken(@PathVariable String token) {
        if (tokenStore instanceof JdbcTokenStore) {
            ((JdbcTokenStore) tokenStore).removeRefreshToken(token);
        }
        return token;
    }

}
