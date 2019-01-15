package com.auth.service;

import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Gu danpeng
 * @date: 2019-1-15
 * @version：1.0
 */
@Service
public class ClientManageService {

    @Autowired
    @Qualifier("jdbcClientDetailService")
    private ClientDetailsService clientDetailsService;

    public List<ClientDetails> getClientDetails() {
        if(JdbcClientDetailsService.class.isInstance(clientDetailsService)){
            return  ((JdbcClientDetailsService) clientDetailsService).listClientDetails();
        }
        return null;
    }
}
