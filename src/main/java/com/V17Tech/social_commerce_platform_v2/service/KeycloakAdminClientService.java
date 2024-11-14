package com.V17Tech.social_commerce_platform_v2.service;


import com.V17Tech.social_commerce_platform_v2.model.UserDTO;

public interface KeycloakAdminClientService {
    UserDTO createKeycloakUser(UserDTO user);

}
