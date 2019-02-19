package com.client1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */
@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.oauth2.client.access-token-uri}")
    private String oauthHost;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/home","/api/**").permitAll().antMatchers("/**").authenticated()
                .and()
                .logout()//.logoutSuccessUrl("/home")
                .deleteCookies().logoutSuccessHandler(
                (request, response, authentication) -> {
//                    response.setHeader("Access-Control-Allow-Origin", "*");
//                    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//                    response.setHeader("Access-Control-Max-Age", "3600");
//                    response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization");
//                    response.sendRedirect("/client1/home");
//                    response.setStatus(HttpStatus.OK.value());
                    response.sendRedirect(oauthHost.split("oauth")[0] + "oauth/exit");
//                    response.sendRedirect(oauthHost.split("oauth")[0] + "logout?callback=http://" +  request.getHeader("Host") + "/client1/home");
                })
                .and()
                .csrf().disable();
//            .authorizeRequests()
//            .antMatchers("/").permitAll()
//            .and()
//            .authorizeRequests().anyRequest().authenticated();
//                .and()
//                .addFilterBefore(filter(), BasicAuthenticationFilter.class);
    }

//    private OAuth2ClientAuthenticationProcessingFilter filter(){
//        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/loginClient1");
//
//        //设置回调成功的页面
//        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler() {
//            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                this.setDefaultTargetUrl("/");
//                System.out.println("OAuth2Client Authentication CallBack");
//                super.onAuthenticationSuccess(request, response, authentication);
//            }
//        });
//        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(){
//            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                System.out.println("OAuth2Client Authentication CallBack Failed");
//                super.onAuthenticationFailure(request, response, exception);
//            }
//        });
//        System.out.println("Filter OAuth2Client Created");
//        return filter;
//    }

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;

    @Value("${security.oauth2.client.user-authorization-uri}")
    private String userAuthorizationUri;

    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails(){
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientId(this.clientId);
        details.setClientSecret(this.clientSecret);
        details.setAccessTokenUri(this.accessTokenUri);
        return  details;
    }

    @Bean("credentialTemplate")
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext context) {
        OAuth2ProtectedResourceDetails details = this.oAuth2ProtectedResourceDetails();
        OAuth2RestTemplate template = new OAuth2RestTemplate(details,context);
        ClientCredentialsAccessTokenProvider authCodeProvider = new ClientCredentialsAccessTokenProvider();
        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));
        template.setAccessTokenProvider(provider);
        return template;
    }

}