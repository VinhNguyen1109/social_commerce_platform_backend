package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.TypeReceiveNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeReceiveNotificationRepository extends JpaRepository<TypeReceiveNotificationEntity, Long> {
    TypeReceiveNotificationEntity getFirstByDescription(String description);

}
