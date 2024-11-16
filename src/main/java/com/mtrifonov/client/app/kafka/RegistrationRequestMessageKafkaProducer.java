package com.mtrifonov.client.app.kafka;

import com.mtrifonov.client.app.supportclasses.RegistrationRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @Mikhail Trifonov
 */
@Component
public class RegistrationRequestMessageKafkaProducer {
    
    private final KafkaTemplate<String, RegistrationRequest> kafkaTemplate;

    public RegistrationRequestMessageKafkaProducer(KafkaTemplate<String, RegistrationRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void produce(RegistrationRequest request) {
        kafkaTemplate.send("registration-request-messages", "registration", request);
    }
}
