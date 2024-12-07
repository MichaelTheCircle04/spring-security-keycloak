package com.mtrifonov.keycloak.registration.service.services;

import com.mtrifonov.client.app.supportclasses.RegistrationRequest;
import java.util.Collections;
import java.util.List;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @Mikhail Trifonov
 */
@Service
public class RegistrationService {
    
    private final Keycloak keycloak;
    
    @Value("${keycloak.realm}")
    private String realm;

    @Autowired
    public RegistrationService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }
    
    public void registerUser(RegistrationRequest request) {
        String userName = request.getUserName();
        CredentialRepresentation credentials = createCredential(request.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userName);
        user.setCredentials(Collections.singletonList(credentials));
        user.setEnabled(true);
        UsersResource usersResource = getUsersResource();
        usersResource.create(user);
        addRoleToUser(userName, request.getRole());
    }
    
    private void addRoleToUser(String userName, String role) {
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().search(userName);
        UserResource user = realmResource.users().get(users.get(0).getId());
        RoleRepresentation roleRepresentation = realmResource.roles().get(role).toRepresentation();
        RoleMappingResource roleMappingResource = user.roles();
        roleMappingResource.realmLevel().add(Collections.singletonList(roleRepresentation));   
    }
    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    } 
    
    private CredentialRepresentation createCredential(String password) {
        var credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
        
    }
}
