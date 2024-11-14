package com.V17Tech.social_commerce_platform_v2.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private String firstname;
    private String lastname;
    private String username;
}
