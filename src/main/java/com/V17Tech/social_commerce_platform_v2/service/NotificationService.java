package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;
import reactor.core.publisher.Mono;

public interface NotificationService {
     void sendNotification( NotificationEntity notification);

     NotificationEntity createNotification(NotificationEntity data);
}
