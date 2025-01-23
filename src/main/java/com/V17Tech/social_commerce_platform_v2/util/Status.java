package com.V17Tech.social_commerce_platform_v2.util;

public enum Status {
    ACTIVE(1L),
    DELETE(2L);

    private final Long value;

    Status(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

}

