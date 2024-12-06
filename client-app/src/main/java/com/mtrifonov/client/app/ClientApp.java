package com.mtrifonov.client.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;


/**
 *
 * @Mikhail Trifonov
 */
@SpringBootApplication
public class ClientApp {
    
    @Value("${resource-server.url}")
    private String resourceServerUrl;
    
    public static void main(String[] args) {
        SpringApplication.run(ClientApp.class, args);
    }
    
    @Bean
    public RestClient restClient(OAuth2AuthorizedClientManager manager) {
        return RestClient.builder().baseUrl(resourceServerUrl)
                .requestInterceptor((request, body, execution) -> {
                    if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                        var token = manager.authorize(OAuth2AuthorizeRequest
                                .withClientRegistrationId("keycloak")
                                .principal(SecurityContextHolder
                                        .getContext()
                                        .getAuthentication())
                                .build())
                                .getAccessToken().getTokenValue();
                        request.getHeaders().setBearerAuth(token);
                    }
                    return execution.execute(request, body);
        }).build();
    }
    /**
     * @param registrationRepo - репозиторий, в котором хранятся данные о зарегестрированных клиентах
     * @param clientRepo - репозиторий, в котором хранятся авторизованные клиенты
     * @return 
    */
    @Bean
    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(ClientRegistrationRepository registrationRepo, 
            OAuth2AuthorizedClientRepository clientRepo) {
        return new DefaultOAuth2AuthorizedClientManager(registrationRepo, clientRepo);
    }
}
