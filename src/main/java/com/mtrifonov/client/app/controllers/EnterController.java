package com.mtrifonov.client.app.controllers;

import com.mtrifonov.client.app.kafka.RegistrationRequestMessageKafkaProducer;
import com.mtrifonov.client.app.supportclasses.RegistrationRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

/**
 *
 * @Mikhail Trifonov
 */
@Controller
public class EnterController {
    
    private final RegistrationRequestMessageKafkaProducer registrationProducer;
    private final RestClient restClient;

    @Autowired
    public EnterController(RegistrationRequestMessageKafkaProducer registrationProducer, 
            RestClient restClient) {
        this.registrationProducer = registrationProducer;
        this.restClient = restClient;
    }
    
    @GetMapping("/auth")
    public ResponseEntity<String> produceAuthenticatedPage() {
        String resource = restClient.get().uri("/user-resource").retrieve().body(String.class);
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(resource);
    }
    
    @GetMapping("/admin")
    public ResponseEntity<String> produceAdminPage() {
        String resource = restClient.get().uri("/admin-resource").retrieve().body(String.class);
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(resource);
    }
    
    @GetMapping("/registration")
    public String produceRegistrationPage() {
        return "registration";
    }
    
    @PostMapping("/registration")
    public String registerUser(HttpServletRequest request) {
        registrationProducer.produce(RegistrationRequest.toRegistrationRequest(request));
        return "main";
    }
    
    @GetMapping("/main")
    public String produceMainPage() {
        return "main";
    }
}
