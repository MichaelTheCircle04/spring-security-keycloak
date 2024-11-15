package com.mtrifonov.client.app.configs;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @Mikhail Trifonov
 */
@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http.oauth2Login(Customizer.withDefaults())
               .oauth2Client(Customizer.withDefaults())
               .authorizeHttpRequests(c -> c.requestMatchers("/error", "/registration", "/main").permitAll()
                       .anyRequest().authenticated())
               .build();
    }
    
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2UserService() {
        OidcUserService service = new OidcUserService();
        return requst -> {
            OidcUser user = service.loadUser(requst);
            List<GrantedAuthority> ath = user.getClaimAsStringList("spring_security_roles")
                    .stream()
                    .filter(r -> r.startsWith("ROLE"))
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast).toList();
            return new DefaultOidcUser(ath, user.getIdToken(), user.getUserInfo());
        };
    }
}
