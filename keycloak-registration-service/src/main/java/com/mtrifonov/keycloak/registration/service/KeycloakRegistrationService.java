package com.mtrifonov.keycloak.registration.service;

import com.mtrifonov.client.app.supportclasses.RegistrationRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 *
 * Mikhail Trifonov
 */
@SpringBootApplication
public class KeycloakRegistrationService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String server;
    
    public static void main(String[] args) {
        SpringApplication.run(KeycloakRegistrationService.class, args);
    }
    
    @Bean 
    public ConsumerFactory<String, RegistrationRequest> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, RegistrationRequest.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RegistrationRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RegistrationRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
