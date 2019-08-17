package com.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author: Gu danpeng
 * @data: 2018-11-12
 * @version：1.0
 */
@Primary
@Component
public class RayTokenEnhancer implements TokenEnhancer {

    private final RedisStringCommands redisStringCommands;

    @Value("${jwt-key}")
    private String jwtKey;

    @Autowired
    public RayTokenEnhancer(RedisStringCommands redisStringCommands) {
        this.redisStringCommands = redisStringCommands;
    }

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String jwt = this.encode(accessToken,authentication);
        this.redisStringCommands.set(accessToken.getValue().getBytes(),jwt.getBytes(),
                Expiration.seconds(Integer.valueOf(accessToken.getExpiresIn()).longValue()),
                RedisStringCommands.SetOption.UPSERT);
        System.out.println("===TokenEnhancer===");
        System.out.println("===TokenEnhancer Jwt=== \n" + jwt);
        return accessToken;
    }

    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map content;
        try {
            content = this.tokenConverter.convertAccessToken(accessToken, authentication);
        } catch (Exception var5) {
            throw new IllegalStateException("Cannot convert access token to JSON", var5);
        }

        return Jwts.builder().setPayload(content.toString())
                .signWith(SignatureAlgorithm.HS256, jwtKey.getBytes())
                .setExpiration(accessToken.getExpiration())
                .compact();
    }

    protected Map<String, Object> decode(String token) {
        return (Map<String, Object>) Jwts.parser().parse(token).getBody();
    }
}
