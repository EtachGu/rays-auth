package com.auth.service;

import com.auth.entity.OAuthAccessToken;
import com.auth.mapper.OAuthAccessTokenMapper;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  管理访问令牌
 *
 * @author: Gu danpeng
 * @date: 2019-1-2
 * @version：1.0
 */
@Service
public class TokenManageService {

    @Autowired
    private OAuthAccessTokenMapper oauthAccessTokenMapper;

   public List<OAuthAccessToken> getAccessTokens(){
       return oauthAccessTokenMapper.selectAll();
   }

   public List<OAuthAccessToken> getDetailAccessTokens(){
       List<OAuthAccessToken> listAccessToken = oauthAccessTokenMapper.selectAll();
       for (OAuthAccessToken fullToken : listAccessToken) {
           OAuth2AccessToken token = (OAuth2AccessToken) SerializationUtils.deserialize(fullToken.getToken());

           OAuth2Authentication auth2Authentication = (OAuth2Authentication) SerializationUtils.deserialize((fullToken.getAuthentication()));
           fullToken.setOAuth2AccessToken(token);
           fullToken.setOAuth2Authentication(auth2Authentication);
       }
       return listAccessToken;
   }

   public OAuthAccessToken getDetailAccessToken(String authenticationId){
       OAuthAccessToken fullToken = oauthAccessTokenMapper.selectByPrimaryKey(authenticationId);
       OAuth2AccessToken token = (OAuth2AccessToken) SerializationUtils.deserialize(fullToken.getToken());

       OAuth2Authentication auth2Authentication = (OAuth2Authentication) SerializationUtils.deserialize((fullToken.getAuthentication()));
       fullToken.setOAuth2AccessToken(token);
       fullToken.setOAuth2Authentication(auth2Authentication);
       return fullToken;
   }
}
