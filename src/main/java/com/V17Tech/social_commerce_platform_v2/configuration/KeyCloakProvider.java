package com.V17Tech.social_commerce_platform_v2.configuration;

import com.V17Tech.social_commerce_platform_v2.model.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Component
@Getter
public class KeyCloakProvider {

    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(KeyCloakProvider.class);
    @Value("${keycloak.auth.server.url}")
    public String serverURL;
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.clientId}")
    public String clientID;

    @Value("${keycloak.admin.username}")
    private String username;
    @Value("${keycloak.admin.password}")
    private String password;

    @Value("${keycloak.credential.secret-admin}")
    private String ADMIN_SECRET;
    @Value("${keycloak.realm}")
    private String REALM;
    private static Keycloak keycloak = null;

    public KeyCloakProvider() {
    }

    public KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password) {
        return KeycloakBuilder.builder()
                .realm(realm)
                .serverUrl(serverURL)
                .clientId(clientID)
                .username(username)
                .password(password);
    }

    public TokenResponse refreshToken(String refreshToken) throws UnirestException, IOException {
        String url = serverURL + "/realms/" + realm + "/protocol/openid-connect/token";
        HttpResponse<JsonNode> response = Unirest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("client_id", clientID)
                .field("refresh_token", refreshToken)
                .field("grant_type", "refresh_token")
                .asJson();

        return objectMapper.readValue(response.getBody().toString(), TokenResponse.class);
    }


    public UsersResource getKeycloakUserResource() {
        logger.warn("-------------------------------------");
        logger.warn(serverURL);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Keycloak kc = KeycloakBuilder.builder()
                .grantType(OAuth2Constants.PASSWORD)
                .serverUrl(serverURL)
                .realm("master")
                .username(username)
                .password(password)
                .clientId("admin-cli")//vinhnc
                .clientSecret(ADMIN_SECRET)
                .resteasyClient(ResteasyClientBuilder.newBuilder()
                        .executorService(executorService)
                        .build())
                .build();
        RealmResource realmResource = kc.realm(REALM);
        return realmResource.users();
    }

    public RealmResource getRealmResource() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Keycloak kc = KeycloakBuilder.builder()
                .grantType(OAuth2Constants.PASSWORD)
                .serverUrl(serverURL)
                .realm("master")
                .username(username)
                .password(password)
                .clientId("admin-cli")//vinhnc
                .clientSecret(ADMIN_SECRET)
                .resteasyClient(ResteasyClientBuilder.newBuilder()
                        .executorService(executorService)
                        .build())
                .build();
        return kc.realm(REALM);
    }
}
