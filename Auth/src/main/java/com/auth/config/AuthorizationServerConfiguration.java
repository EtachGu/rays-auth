package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * @author: Gu danpeng
 * @date: 2018-12-31
 * @version：1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

//    @Autowired
//    ApplicationContext applicationContext;

    @Autowired
//    @Qualifier("mysql")
    private DataSource dataSource;

//    @Autowired
//    private RedisConnectionFactory connectionFactory;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//        @Autowired
//        private UserApprovalHandler userApprovalHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(this.dataSource);
    }

//    @Bean
//    public TokenStore tokenStore() {
//        RedisTokenStore redis = new RedisTokenStore(connectionFactory);
//        return redis;
//    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(this.dataSource);
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setReuseRefreshToken(true);
        // set Access Token Expiration
//        defaultTokenServices.setAccessTokenValiditySeconds(30);
        return defaultTokenServices;
    }

    @Bean("jdbcClientDetailService")
    public ClientDetailsService clientDetails(){
            Assert.state(this.dataSource != null, "You need to provide a DataSource");
            JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(this.dataSource);
            if (this.passwordEncoder != null) {
                clientDetailsService.setPasswordEncoder(this.passwordEncoder);
            }
            return clientDetailsService;
    }

//    @Bean
//    public AccessTokenConverter defaultTokenConverter(){
//
//    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // @formatter:off
//        JdbcClientDetailsServiceBuilder builder = new JdbcClientDetailsServiceBuilder();
//        builder.dataSource(this.dataSource);
//        builder.passwordEncoder(passwordEncoder());
//        clients.configure(builder);
        System.out.println("Password" + passwordEncoder.encode("123456"));
//        clients.jdbc(this.dataSource);
        clients.withClientDetails(clientDetails());
//        clients.inMemory()
//                .withClient("client1")
//                .authorizedGrantTypes("authorization_code", "refresh_token", "password","client_credentials")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret(passwordEncoder.encode("123456"))
//                .redirectUris("http://localhost:8081/client1/login","http://127.0.0.1:8081","http://127.0.0.1:8081/client1/login")
////                .autoApprove(true)
//                .and()
//                .withClient("client2")
//                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret(passwordEncoder.encode("123456"))
////                .autoApprove(true)
//                .redirectUris("http://localhost:8082/client2/login","http://127.0.0.1:8082/client2/login");
        // @formatter:on

    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST) // 允许通过Get和POST请求 获取Token
                .tokenStore(tokenStore())//.userApprovalHandler(userApprovalHandler)
                .approvalStore(approvalStore())
                .authenticationManager(authenticationManager)
        .pathMapping("/oauth/check_token","/oauth/check_token")
                .tokenServices(defaultTokenServices());
//                .accessTokenConverter(defaultTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //oauthServer.realm("sparklr2/client");
//            oauthServer.checkTokenAccess()
        security.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_CLIENT')");
        security.checkTokenAccess("hasAuthority('ROLE_CLIENT')");
//        security.allowFormAuthenticationForClients(); // 允许
    }

}