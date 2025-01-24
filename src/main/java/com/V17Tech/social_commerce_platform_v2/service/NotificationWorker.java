package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.AccountEntity;
import com.V17Tech.social_commerce_platform_v2.entity.HistorySendNotificationEntity;
import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;
import com.V17Tech.social_commerce_platform_v2.entity.NotificationReceiverEntity;
import com.V17Tech.social_commerce_platform_v2.model.SendEmailDTO;
import com.V17Tech.social_commerce_platform_v2.repository.HistorySendNotificationRepository;
import com.V17Tech.social_commerce_platform_v2.repository.NotificationReceiverRepository;
import com.V17Tech.social_commerce_platform_v2.sender.KafkaSender;
import com.V17Tech.social_commerce_platform_v2.util.TypeReceive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationWorker {
    private final KafkaSender sender;

    private final NotificationReceiverRepository receiverRepository;

    private final HistorySendNotificationRepository historySendNotificationRepository;
    private final Logger logger = LoggerFactory.getLogger(NotificationWorker.class);

    @Value("${spring.mail.username}")
    private String fromEamil;
    @Value("${spring.kafka.topics.send-email}")
    private String topicSendEmail;
    @Async
    public void sendBatch(List<AccountEntity> accountList, NotificationEntity notification, String typeReceive) {
        for (AccountEntity account : accountList) {
            if(receiverRepository.getFirstByUsername(account.getUsername()) == null){
                receiverRepository.save(NotificationReceiverEntity.builder()
                        .email(account.getEmail())
                        .username(account.getUsername())
                        .build());
            }
            NotificationReceiverEntity receiver = receiverRepository.getFirstByUsername(account.getUsername());

            HistorySendNotificationEntity history = HistorySendNotificationEntity.builder()
                    .notificationId(notification.getId())
                    .chanel(typeReceive)
                    .notificationReceiverId(receiver.getId())
                    .build();
            historySendNotificationRepository.save(history);
            if(typeReceive == TypeReceive.EMAIL.getValue()){
                SendEmailDTO sendEmailDTO = SendEmailDTO.builder()
                        .fromEmail(fromEamil)
                        .toEmail(receiver.getEmail())
                        .title(notification.getTitle())
                        .content(notification.getContent())
                        .build();
                logger.info("checkkk send =====================");
                sender.send(topicSendEmail, sendEmailDTO);
                logger.info("Đã gửi email cho người dùng: {}", account.getUsername());
            }
        }
        logger.info("Hoàn thành gửi thông báo cho batch với {} người dùng", accountList.size());
    }


}
