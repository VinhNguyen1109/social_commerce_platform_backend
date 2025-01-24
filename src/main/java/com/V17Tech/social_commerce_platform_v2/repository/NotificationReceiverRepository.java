package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationReceiverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationReceiverRepository extends JpaRepository<NotificationReceiverEntity, Long> {
    NotificationReceiverEntity getFirstByUsername(String username);

    NotificationReceiverEntity getFirstById(Long id);
}
