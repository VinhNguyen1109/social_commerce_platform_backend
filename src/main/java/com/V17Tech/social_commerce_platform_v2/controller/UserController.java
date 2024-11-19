package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.configuration.KeyCloakProvider;
import com.V17Tech.social_commerce_platform_v2.model.LoginRequest;
import com.V17Tech.social_commerce_platform_v2.model.LoginUserDTO;
import com.V17Tech.social_commerce_platform_v2.model.UserDTO;
import com.V17Tech.social_commerce_platform_v2.sender.KafkaSender;
import com.V17Tech.social_commerce_platform_v2.service.AccountService;
import com.V17Tech.social_commerce_platform_v2.service.KeycloakAdminClientService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final AccountService accountService;
    private final KeycloakAdminClientService kcAdminClient;
    private final KeyCloakProvider kcProvider;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaSender sender;

    @Value("${spring.kafka.topics.test-kafka}")
    private String testTopic;
    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(kcAdminClient.createKeycloakUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody LoginRequest loginRequest) {
        sender.send(testTopic, "check kafka");
        logger.info("check debug working");
            LoginUserDTO loginUserDTO = accountService.login(loginRequest);
            if(loginUserDTO == null){
                return ResponseEntity.status(HttpStatus.OK).body("Tài khoản không tồn tại");
            }
                return ResponseEntity.status(HttpStatus.OK).body(loginUserDTO);
    }

    @PostMapping("/access")
    public ResponseEntity<?> getAccessTokenFromRefreshToken(@RequestBody String refreshToken) throws UnirestException, IOException {
        return ResponseEntity.ok(kcProvider.refreshToken(refreshToken));
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestHeader(name = "newpassword", required = true) String newPassword,
                                           @RequestHeader(name = "Authorization", required = true) String token){

        return ResponseEntity.status(HttpStatus.OK).body(accountService.resetPassword( token, newPassword));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "Authorization", required = true) String token){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.logout( token));
    }

}
