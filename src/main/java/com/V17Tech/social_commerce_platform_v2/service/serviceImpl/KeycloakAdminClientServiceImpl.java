package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.configuration.KeyCloakProvider;
import com.V17Tech.social_commerce_platform_v2.entity.AccountEntity;
import com.V17Tech.social_commerce_platform_v2.entity.RoleEntity;
import com.V17Tech.social_commerce_platform_v2.service.AccountService;
import com.V17Tech.social_commerce_platform_v2.service.KeycloakAdminClientService;
import com.V17Tech.social_commerce_platform_v2.service.RoleService;
import lombok.RequiredArgsConstructor;
import com.V17Tech.social_commerce_platform_v2.model.UserDTO;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeycloakAdminClientServiceImpl implements KeycloakAdminClientService {
    private static final Logger logger = LoggerFactory.getLogger(KeycloakAdminClientServiceImpl.class);
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.clientId}")
    public String CLIENT_ID;


    private final KeyCloakProvider kcProvider;
    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;


    public UserDTO createKeycloakUser(UserDTO userDTO) {
        UsersResource usersResource = kcProvider.getKeycloakUserResource();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(userDTO.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(userDTO.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(userDTO.getFirstname());
        kcUser.setLastName(userDTO.getLastname());
        kcUser.setEmail(userDTO.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        Response response = usersResource.create(kcUser);
        if(response.getStatus() == 201){
            //lấy userid trong keycloak
            //lấy phần cuối cùng của path
            String userIdKeycloak = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            userDTO.setKeycloakId(userIdKeycloak);
            logger.debug("get keycloak user id with value: " + userIdKeycloak);
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(userDTO.getPassword());

            //set password
            usersResource.get(userIdKeycloak).resetPassword(passwordCred);

            //set realm role
            RealmResource realmResource = kcProvider.getRealmResource();
            //muốn add nhiều role thì duplicate nhiều lần sau đó chuyển thành collections rồi add
            RoleRepresentation savedRoleRepresentation = realmResource.roles().get("user").toRepresentation();
            realmResource.users().get(userIdKeycloak).roles().realmLevel().add(Collections.singletonList(savedRoleRepresentation));

            //set client role: it like the role in the system
            ClientRepresentation clientRep = realmResource.clients().findByClientId(CLIENT_ID).get(0);
            RoleRepresentation clientRoleRep = realmResource.clients().get(clientRep.getId()).roles().get("client_user").toRepresentation();
            realmResource.users().get(userIdKeycloak).roles().clientLevel(clientRep.getId()).add(Collections.singletonList(clientRoleRep));


            //set role
            RoleEntity role = roleService.getRoleByName("client_user");
            if(role == null) logger.info("role is null when get by name");
            accountService.saveAccount(AccountEntity.builder()
                            .lastname(userDTO.getLastname())
                            .firstname(userDTO.getFirstname())
                            .keycloakId(userDTO.getKeycloakId())
                            .email(userDTO.getEmail())
                            .username(userDTO.getUsername())
                            .password(passwordEncoder.encode(userDTO.getPassword()))
                            .role(role)
                    .build());
            System.out.println("password befor encode: " +
                    userDTO.getPassword());
            logger.info("create success fully");
        } else {
            logger.error("Fail to create: " + response.readEntity(String.class));
        }
        return userDTO;
    }


    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }


}
