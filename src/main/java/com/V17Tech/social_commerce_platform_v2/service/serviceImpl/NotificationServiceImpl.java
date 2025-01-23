package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.configuration.APIExceptionHandaller;
import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;
import com.V17Tech.social_commerce_platform_v2.repository.NotificationRepository;
import com.V17Tech.social_commerce_platform_v2.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationServiceImpl implements NotificationService {
    Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final NotificationRepository notiRepo;

    private final RestTemplate restTemplate;

    private final String apiUrl = "http://localhost:8081/api/auth/users/count";

    @Override
    public void sendNotification() {
        logger.info(callCountApi() + "");
    }
    public Long callCountApi() {
        ResponseEntity<Long> response = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null, Long.class);
        return response.getBody();
    }
    @Override
    public NotificationEntity createNotification(NotificationEntity data) {
        return notiRepo.save(data);
    }
}
