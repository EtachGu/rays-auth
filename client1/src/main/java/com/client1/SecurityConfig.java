//package com.client1;
//
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.csrf.CsrfFilter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author: Gu danpeng
// * @data: 2018-12-2
// * @version：1.0
// */
//@Configuration
//@EnableOAuth2Sso
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
////            .authorizeRequests()
////            .antMatchers("/").permitAll()
////            .and()
//            .authorizeRequests().anyRequest().authenticated();
////                .and()
////                .addFilterBefore(filter(), BasicAuthenticationFilter.class);
//    }
//
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
//}