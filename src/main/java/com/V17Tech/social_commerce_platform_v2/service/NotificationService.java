package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;

public interface NotificationService {
    public void sendNotification();

    public NotificationEntity createNotification(NotificationEntity data);
}
