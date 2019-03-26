package com.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller which decodes access tokens for clients who are not able to do so (or where opaque token values are used).
 *
 * @author Gu danpeng
 * @data 2019-3-25
 * @version 1.0
 */
@Controller
public class CheckTokenController {

    @Autowired
    LettuceConnectionFactory lettuceConnectionFactory;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisStringCommands stringCommands;

	@Autowired
	private ResourceServerTokenServices resourceServerTokenServices;

	private AccessTokenConverter accessTokenConverter = new CheckTokenAccessTokenConverter();

	protected final Logger logger = LoggerFactory.getLogger(CheckTokenController.class);

	private WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator = new DefaultWebResponseExceptionTranslator();

	
	/**
	 * @param exceptionTranslator the exception translator to set
	 */
	public void setExceptionTranslator(WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator) {
		this.exceptionTranslator = exceptionTranslator;
	}

	/**
	 * @param accessTokenConverter the accessTokenConverter to set
	 */
	public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
		this.accessTokenConverter = accessTokenConverter;
	}

//	@Cacheable("check_token")
	@RequestMapping(value = "/oauth/check_token")
	@ResponseBody
	public Map<String, ?> checkToken(@RequestParam("token") String value) {

        String keyStr ="check_token::" + value;
//        logger.info(String.format("Current Thread: %d, StringCommands HashCode: %d, Connection HashCode ",
//				Thread.currentThread().getId(),
//				stringCommands.hashCode()));
////				.hashCode()));
        if(stringCommands.get(keyStr.getBytes()) != null){
            return (HashMap<String,?>) SerializationUtils.deserialize(stringCommands.get(keyStr.getBytes()));
        }


		OAuth2AccessToken token = resourceServerTokenServices.readAccessToken(value);
		if (token == null) {
			throw new InvalidTokenException("Token was not recognised");
		}

		if (token.isExpired()) {
			throw new InvalidTokenException("Token has expired");
		}

		OAuth2Authentication authentication = resourceServerTokenServices.loadAuthentication(token.getValue());

        Map<String, ?> accessTokenMap = accessTokenConverter.convertAccessToken(token, authentication);
        stringCommands.set(keyStr.getBytes(),
                SerializationUtils.serialize(accessTokenMap),
                Expiration.seconds(Integer.valueOf(token.getExpiresIn()).longValue()),
                RedisStringCommands.SetOption.UPSERT
        );
		return accessTokenMap;
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
		logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
		// This isn't an oauth resource, so we don't want to send an
		// unauthorized code here. The client has already authenticated
		// successfully with basic auth and should just
		// get back the invalid token error.
		@SuppressWarnings("serial")
		InvalidTokenException e400 = new InvalidTokenException(e.getMessage()) {
			@Override
			public int getHttpErrorCode() {
				return 400;
			}
		};
		return exceptionTranslator.translate(e400);
	}

	static class CheckTokenAccessTokenConverter implements AccessTokenConverter {
		private final AccessTokenConverter accessTokenConverter;

		CheckTokenAccessTokenConverter() {
			this(new DefaultAccessTokenConverter());
		}

		CheckTokenAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
			this.accessTokenConverter = accessTokenConverter;
		}

		@Override
		public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
			Map<String, Object> claims = (Map<String, Object>) this.accessTokenConverter.convertAccessToken(token, authentication);

			// gh-1070
			claims.put("active", true);		// Always true if token exists and not expired

			return claims;
		}

		@Override
		public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
			return this.accessTokenConverter.extractAccessToken(value, map);
		}

		@Override
		public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
			return this.accessTokenConverter.extractAuthentication(map);
		}
	}
}
