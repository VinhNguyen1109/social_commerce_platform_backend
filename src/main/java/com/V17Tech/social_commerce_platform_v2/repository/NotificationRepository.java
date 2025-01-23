package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
