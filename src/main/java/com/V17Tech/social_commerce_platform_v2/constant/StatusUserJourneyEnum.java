package com.V17Tech.social_commerce_platform_v2.constant;

import lombok.Getter;

@Getter
public enum StatusUserJourneyEnum {
    SAVE(1),
    NOT_SAVE(0);

    private final int value;

    StatusUserJourneyEnum(int value) {
        this.value = value;
    }
}
