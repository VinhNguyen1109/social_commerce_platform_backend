package com.V17Tech.social_commerce_platform_v2.constant;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("client_user"),
    USER("client_admin");

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }
}

