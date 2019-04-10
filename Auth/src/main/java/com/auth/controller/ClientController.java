package com.auth.controller;

import com.auth.entity.OAuthClientDetails;
import com.auth.mapper.OAuthClientDetailsMapper;
import com.auth.service.ClientManageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gu danpeng
 * @version 1.0
 * @date 2019-3-12
 */
@Validated
@PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
@Controller
public class ClientController {

    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientManageService clientManageService;

    @Autowired
    private OAuthClientDetailsMapper oAuthClientDetailsMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public String clients(Model model,
                          @RequestParam(value = "clientId",required = false) String clientId){
        List<ClientDetails> clients =  clientManageService.getClientDetails();
        model.addAttribute("clients",clients);
        return "clients";
    }

    @GetMapping("/edit-client")
    public String editClient(Model model,
                             @RequestParam("clientId") String clientId){
        OAuthClientDetails client = oAuthClientDetailsMapper.selectByPrimaryKey(clientId);

        boolean errorInfo = true;
        if(client != null){
            List<String> grantTypes = clientManageService.getGrantTypes(client);
            List<String> redirectUris = clientManageService.getRedirectUris(client);

            model.addAttribute("client", client);
            model.addAttribute("grantTypes", grantTypes);
            model.addAttribute("redirectUris", redirectUris);
            errorInfo = false;
        }
        model.addAttribute("errorInfo",errorInfo);
        return "edit-client";
    }

    @PostMapping("/edit-client")
    public String updateClient(@RequestParam String clientId,
                             @RequestParam(value = "clientSecret",required = false) String clientSecret,
                             @RequestParam(value = "authorizationCode",required = false) String authorizationCode,
                             @RequestParam(value = "password",required = false) String password,
                             @RequestParam(value = "clientCredentials", required = false) String clientCredentials,
                             @RequestParam(value = "refreshToken", required = false) String refreshToken,
                             @RequestParam(value = "scope", required = false) String scope,
                             @RequestParam(value = "autoapprove", required = false) String autoapprove,
                             @RequestParam(value = "authorities", required = false) String authorities,
                             @RequestParam(value = "redirectUris", required = false) List<String> redirectUris,
                             Model model,
                             HttpServletRequest request){
        if(oAuthClientDetailsMapper.existsWithPrimaryKey(clientId)) {
            OAuthClientDetails client  = new OAuthClientDetails();
            client.setClientId(clientId);
            List<String> grantTypesStr = new ArrayList<>(4);
            if ("on".equals(authorizationCode)){
                grantTypesStr.add("authorization_code");
            }
            if ("on".equals(refreshToken)){
                grantTypesStr.add("refresh_token");
            }
            if ("on".equals(password)){
                grantTypesStr.add("password");
            }
            if ("on".equals(clientCredentials)){
                grantTypesStr.add("client_credentials");
            }

            client.setAuthorizedGrantTypes(StringUtils.join(grantTypesStr,","));
            client.setScope(scope);
            client.setAutoapprove(autoapprove);
            client.setAuthorities(authorities);
            client.setWebServerRedirectUri(StringUtils.join(redirectUris,","));

            clientManageService.updateClientDetails(OAuthClientDetails.ClientDetailsBuilder(client));
        }
        return "redirect:/clients";
    }

    @GetMapping("/new-client")
    public String newClient(){
        return "new-client";
    }

    @PostMapping("/new-client")
    public String newClient(@NotBlank @RequestParam("clientId") String clientId,
                            @RequestParam("clientSecret") String clientSecret,
                            @RequestParam(value = "authorizationCode",required = false) String authorizationCode,
                            @RequestParam(value = "password",required = false) String password,
                            @RequestParam(value = "clientCredentials", required = false) String clientCredentials,
                            @RequestParam(value = "refreshToken", required = false) String refreshToken,
                            @RequestParam(value = "scope", required = false) String scope,
                            @RequestParam(value = "autoapprove", required = false) String autoapprove,
                            @RequestParam(value = "authorities", required = false) String authorities,
                            @RequestParam(value = "redirectUris", required = false) List<String> redirectUris,
                            Model model,
                            HttpServletRequest request){
        boolean isSuccessed = false;
        if(!oAuthClientDetailsMapper.existsWithPrimaryKey(clientId)) {
            OAuthClientDetails client  = new OAuthClientDetails();
            client.setClientId(clientId);
            client.setClientSecret(passwordEncoder.encode(clientSecret));
            List<String> grantTypesStr = new ArrayList<>(4);
            if ("on".equals(authorizationCode)){
                grantTypesStr.add("authorization_code");
            }
            if ("on".equals(refreshToken)){
                grantTypesStr.add("refresh_token");
            }
            if ("on".equals(password)){
                grantTypesStr.add("password");
            }
            if ("on".equals(clientCredentials)){
                grantTypesStr.add("client_credentials");
            }

            client.setAuthorizedGrantTypes(StringUtils.join(grantTypesStr,","));
            client.setScope(scope);
            client.setAutoapprove(autoapprove);
            client.setAuthorities(authorities);
            client.setWebServerRedirectUri(StringUtils.join(redirectUris,","));

            clientManageService.addClientDetails(OAuthClientDetails.ClientDetailsBuilder(client));
        }
        return "redirect:clients";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleException(Exception e, HttpServletRequest request) throws Exception {
        logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        // This isn't an oauth resource, so we don't want to send an
        // unauthorized code here. The client has already authenticated
        // successfully with basic auth and should just
        // get back the invalid token error.

        return "redirect:" + request.getServletPath();
    }

}
