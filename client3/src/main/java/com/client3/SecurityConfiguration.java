package com.client3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author: Gu danpeng
 * @date: 2019-2-22
 * @version：1.0
 */

@Configuration
@EnableOAuth2Client
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    @Autowired
    private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

    @Autowired
    private ResourceServerProperties resourceServerProperties;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/webjars/**",
                "/images/**",
                "/**/*.js",
                "/**/*.css",
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.woff2",
                "/oauth/uncache_approvals",
                "/oauth/cache_approvals"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/api/**")
                .permitAll().antMatchers("/**").authenticated()
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
                    response.sendRedirect(authorizationCodeResourceDetails.getAccessTokenUri().split("oauth")[0] + "oauth/exit");
//                    response.sendRedirect(oauthHost.split("oauth")[0] + "logout?callback=http://" +  request.getHeader("Host") + "/client1/home");
                })
                .and()
//                .formLogin().successHandler((request, response, authentication)->{
//                    System.out.println("========== CIMAuthenticationLogin");
//                })
//                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .csrf().disable();
//            .authorizeRequests()
//            .antMatchers("/").permitAll()
//            .and()
//            .authorizeRequests().anyRequest().authenticated();

    }

    @ConfigurationProperties("security.oauth2")
    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }


    @Bean("credentialTemplate")
    public OAuth2RestTemplate oauth2RestTemplate() {
        OAuth2ProtectedResourceDetails details = this.oAuth2ProtectedResourceDetails();
        OAuth2RestTemplate template = new OAuth2RestTemplate(details,this.oauth2ClientContext);
        ClientCredentialsAccessTokenProvider authCodeProvider = new ClientCredentialsAccessTokenProvider();
        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));
        template.setAccessTokenProvider(provider);
        return template;
    }

    @Primary
    @Bean
    OAuth2RestTemplate oAuth2RestTemplate(){
        return new OAuth2RestTemplate(authorizationCodeResourceDetails, this.oauth2ClientContext);
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter oauth2Filter = new OAuth2ClientAuthenticationProcessingFilter("/login");
        oauth2Filter.setRestTemplate(oAuth2RestTemplate());
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), authorizationCodeResourceDetails.getClientId());
        tokenServices.setRestTemplate(oAuth2RestTemplate());
        oauth2Filter.setTokenServices(tokenServices);
        oauth2Filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                response.sendRedirect(this.getDefaultTargetUrl());
                super.onAuthenticationSuccess(request, response, authentication);
            }
        });
        return oauth2Filter;
    }
}