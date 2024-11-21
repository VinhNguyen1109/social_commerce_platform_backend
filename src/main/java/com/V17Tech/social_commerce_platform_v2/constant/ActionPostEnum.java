package com.V17Tech.social_commerce_platform_v2.constant;

import lombok.Getter;

@Getter
public enum ActionPostEnum {
    LIKE("1"),
    SHARE("2"),
    CLICK("3"),

    VIEW("4");

    private final String value;

    ActionPostEnum(String value) {
        this.value = value;
    }
    public static ActionPostEnum fromValue(String value) {
        for (ActionPostEnum type : ActionPostEnum.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + value);
    }
}
