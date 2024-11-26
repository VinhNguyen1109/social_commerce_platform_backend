package com.V17Tech.social_commerce_platform_v2.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaSenderImpl implements KafkaSender {
    Logger logger = LoggerFactory.getLogger(KafkaSenderImpl.class);

    private final KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public <T> void send(String topic, T data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            kafkaTemplate.send(topic, mapper.writeValueAsString(data)).get(5, TimeUnit.SECONDS);
        } catch (JsonProcessingException | InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("Lỗi khi gửi kafka: " + e.getMessage());
        }
    }


}

