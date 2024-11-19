package com.V17Tech.social_commerce_platform_v2.reciever;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaReceiver {
    @KafkaListener(topics = "test-kafka")
    public void checkKafka (String message){
        System.out.println("check message: " + message);
    }
}
