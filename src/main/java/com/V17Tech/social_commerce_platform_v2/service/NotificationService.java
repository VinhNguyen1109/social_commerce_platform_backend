package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;

public interface NotificationService {
     void sendNotification(String typeReceive, NotificationEntity notification);

     NotificationEntity createNotification(NotificationEntity data);
}
