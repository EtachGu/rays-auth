package com.auth.config;

import com.auth.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.request.RequestContextListener;

import javax.sql.DataSource;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserManageService userManageService;

//    @Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("123")).roles("USER").and().withUser("paul")
//                .password("emu").roles("USER");
//    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

//    @Bean
//    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
//        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/oauth/token","/oauth/check_token");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.formLogin().loginPage("/login")
                .defaultSuccessUrl("/home")
                .and().authorizeRequests()
                .antMatchers("/authentication/require",
                        "/authentication/form",
                        "/login",
                        "/register",
                        "/sign_up",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.woff2"
                )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
//                .permitAll()
//                .logoutSuccessHandler(
//                        (request, response, authentication) -> {
//                            String callback = request.getParameter("callback");
//                            if (callback == null){
//                                callback = "/login?logout";
//                            }
//                            response.sendRedirect(callback);
//                        }
//                )
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
                .and()
                .rememberMe()
                .tokenValiditySeconds(604800) // one week
                //指定记住登录信息所使用的数据源
                .tokenRepository(tokenRepository())
                .userDetailsService(userManageService)
                .and()
                .cors()
                .and()
                .csrf().disable();
        // @formatter:on

        http.sessionManagement().maximumSessions(3).sessionRegistry(sessionRegistry());
    }

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}
