package com.mtrifonov.keycloak.registration.service.configs;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Mikhail Trifonov
 */
@Configuration
public class KeycloakConfig {
    
    @Value("${keycloak.server-url}")
    private String serverUrl;
    
    @Value("${keycloak.realm}")
    private String realm;
    
    @Value("${keycloak.client.client-id}")
    private String clientId;
    
    @Value("${keycloak.client.client-secret}")
    private String clientSecret;
    
    @Value("${keycloak.username}")
    private String userName;
    
    @Value("${keycloak.password}")
    private String password;
    
    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(userName)
                .password(password)
                .build();
    }
}
