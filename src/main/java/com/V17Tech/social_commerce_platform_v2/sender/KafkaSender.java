package com.V17Tech.social_commerce_platform_v2.sender;


import com.V17Tech.social_commerce_platform_v2.model.SendEmailDTO;
import reactor.core.publisher.Mono;

public interface KafkaSender {
    <T> void send(String topic, T data);
    <T> Mono<Void> sendMono(String topic, T data);


}
