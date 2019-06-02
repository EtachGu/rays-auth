package com.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;


/**
 * @author: Gu danpeng
 * @date: 2019-1-11
 * @version：1.0
 */
@Configuration
public class AuthCorsConfiguration {

    /**
     *
     */
    @Value("#{'${cors-allowed-origins}'.split(',')}")
    private List<String> corsAllowedOrigins;

    /**
     *
     * @return CorsConfigurationSource
     */
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(corsAllowedOrigins); // 跨域访问的 地址
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
//        corsConfiguration.addExposedHeader("head1");
        //corsConfiguration.addExposedHeader("Location");
        corsConfiguration.setAllowedOrigins(corsAllowedOrigins); // 跨域访问的 地址
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
