package com.mtrifonov.keycloak.registration.service.configs;

import com.mtrifonov.client.app.supportclasses.RegistrationRequest;
import com.mtrifonov.keycloak.registration.service.services.RegistrationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @Mikhail Trifonov
 */
@Component
public class RegistrationRequestMessageKafkaConsumer {
    
    private final RegistrationService registrationService;

    public RegistrationRequestMessageKafkaConsumer(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    
    @KafkaListener(topics = "registration-request-messages", groupId = "registration-listeners")
    public void listen(RegistrationRequest request) {
        registrationService.registerUser(request);
    }   
}
