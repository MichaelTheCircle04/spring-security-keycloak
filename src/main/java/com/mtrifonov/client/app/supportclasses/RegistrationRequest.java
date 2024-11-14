package com.mtrifonov.client.app.supportclasses;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @Mikhail Trifonov
 */
@Data
@AllArgsConstructor
public class RegistrationRequest {
    
    public static RegistrationRequest toRegistrationRequest(HttpServletRequest request) {
        return new RegistrationRequest(request.getParameter("username"), request.getParameter("password"), "ROLE_USER");
    }
    
    private String userName;
    private String password;
    private String role;
}
