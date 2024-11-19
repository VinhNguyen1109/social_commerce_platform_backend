package com.V17Tech.social_commerce_platform_v2.sender;


public interface KafkaSender {
    <T> void send(String topic, T data);
}
