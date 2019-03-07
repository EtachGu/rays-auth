package com.auth.service;

import com.auth.entity.OAuthClientDetails;
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

    public ClientDetails getClientDetailsById(String clientId){
        if(JdbcClientDetailsService.class.isInstance(clientDetailsService)){
            return  ((JdbcClientDetailsService) clientDetailsService).loadClientByClientId(clientId);
        }
        return null;
    }

    public List<ClientDetails> getClientDetails() {
        if(JdbcClientDetailsService.class.isInstance(clientDetailsService)){
            return  ((JdbcClientDetailsService) clientDetailsService).listClientDetails();
        }
        return null;
    }

    public void addClientDetails(ClientDetails clientDetails) {
        if(JdbcClientDetailsService.class.isInstance(clientDetailsService)){
             ((JdbcClientDetailsService) clientDetailsService).addClientDetails(clientDetails);
        }
    }

    public void updateClientDetails(ClientDetails clientDetails) {
        if(JdbcClientDetailsService.class.isInstance(clientDetailsService)){
            ((JdbcClientDetailsService) clientDetailsService).updateClientDetails(clientDetails);
        }
    }

    public void updateClientSecret(String clientId, String secret) {
        if(JdbcClientDetailsService.class.isInstance(clientDetailsService)){
            ((JdbcClientDetailsService) clientDetailsService).updateClientSecret(clientId, secret);
        }
    }

    public void removeClientDetails(String clientId) {
        if(JdbcClientDetailsService.class.isInstance(clientDetailsService)){
            ((JdbcClientDetailsService) clientDetailsService).removeClientDetails(clientId);
        }
    }

    public List<ClientDetails> listClientDetails() {
        if(JdbcClientDetailsService.class.isInstance(clientDetailsService)){
            return  ((JdbcClientDetailsService) clientDetailsService).listClientDetails();
        }
        return null;
    }

    public void insertOAuthClientDetails(OAuthClientDetails oAuthClientDetails){
//        if(JdbcClientDetailsService.class.isInstance(clientDetailsService)){
//            ((JdbcClientDetailsService) clientDetailsService).addClientDetails(oAuthClientDetails);
//        }
    }
}
