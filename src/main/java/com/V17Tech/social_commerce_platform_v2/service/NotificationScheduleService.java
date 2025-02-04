package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationScheduleEntity;

import java.util.List;

public interface NotificationScheduleService {
     NotificationScheduleEntity createSchedule(NotificationScheduleEntity data);
     List<NotificationScheduleEntity> getScheduleWaitWorking();


     String cancelScheduleWithId(Long id);
}
