package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;
import com.V17Tech.social_commerce_platform_v2.entity.TypeReceiveNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> getByNotificationScheduleId(Long notificationScheduleId);

    NotificationEntity getFirstById(Long id);

    @Query("SELECT n.typeReceives FROM NotificationEntity n WHERE n.id = :id")
    List<TypeReceiveNotificationEntity> getTypeReceiveByNotificationId(@Param("id") Long id);


}
