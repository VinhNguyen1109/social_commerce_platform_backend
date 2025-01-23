package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationTypeEntity;

public interface NotificationTypeService {
    public NotificationTypeEntity createType(NotificationTypeEntity data);

    public void deleteType(Long id);

    public void restart(Long id);

}
