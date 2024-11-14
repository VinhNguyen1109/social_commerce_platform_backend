package com.V17Tech.social_commerce_platform_v2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    private String access_token;
    private String refresh_token;
    private Long expires_in;
    private String token_type;

}

