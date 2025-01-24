package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.AccountEntity;
import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;
import com.V17Tech.social_commerce_platform_v2.repository.AccountRepository;
import com.V17Tech.social_commerce_platform_v2.repository.NotificationRepository;
import com.V17Tech.social_commerce_platform_v2.service.NotificationService;
import com.V17Tech.social_commerce_platform_v2.service.NotificationWorker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationServiceImpl implements NotificationService {
    Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Value("${spring.kafka.schedule.batchSize}")
    private int batchSize;
    private final NotificationRepository notiRepo;
    private final RestTemplate restTemplate;
    private final AccountRepository accountRepository;

    private final NotificationWorker notificationWorker;

    private final String apiUrl = "http://localhost:8081/api/auth/users/count";

    @Override
    public void sendNotification(String typeReceive, NotificationEntity notification) {

        Long totalUsers = callCountApi();
        int numberOfWorkers = (int) Math.ceil((double) totalUsers / batchSize);
        logger.info("Tổng số worker cần: " + numberOfWorkers);

        for (int i = 0; i < numberOfWorkers; i++) {
            Long start = (long) (i * batchSize);
            Long end = start + batchSize;
            logger.info("Xử lý từ người dùng thứ: " + start + " đến " + end);
            List<AccountEntity> batchUser = accountRepository.getAccountListToSendNotification(start, end);
            notificationWorker.sendBatch(batchUser, notification, typeReceive);
        }
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
