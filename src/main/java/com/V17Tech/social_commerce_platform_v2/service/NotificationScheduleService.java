package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationScheduleEntity;

import java.util.List;

public interface NotificationScheduleService {
    public NotificationScheduleEntity createSchedule(NotificationScheduleEntity data);

    public List<NotificationScheduleEntity> getScheduleWaitWorking();

    public void doScheduleWaitWorking();

    public void scheduleNotificationWithStart(NotificationScheduleEntity data);
}
