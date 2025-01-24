package com.V17Tech.social_commerce_platform_v2.util;

public enum TypeReceive {
    EMAIL("email"),
    PUSH("push notification");

    private final String value;

    TypeReceive(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
