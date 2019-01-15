package com.auth.service;

import com.auth.entity.OAuthAccessToken;
import com.auth.mapper.OAuthAccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
