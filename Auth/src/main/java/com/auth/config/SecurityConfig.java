package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("123")).roles("USER").and().withUser("paul")
                .password("emu").roles("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.formLogin()
                .and().authorizeRequests()
                .antMatchers("/authentication/require",
                        "/authentication/form",
                        "/login",
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
                .cors()
                .and()
                .csrf().disable();
        // @formatter:on
    }
}
