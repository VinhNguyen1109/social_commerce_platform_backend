package com.V17Tech.social_commerce_platform_v2.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    String username;
    String password;
    String email;
    String firstname;
    String lastname;
    String keycloakId;
}