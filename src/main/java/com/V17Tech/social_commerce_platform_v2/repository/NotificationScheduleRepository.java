package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationScheduleRepository extends JpaRepository<NotificationScheduleEntity, Long> {

    @Query("select ns from NotificationScheduleEntity ns where ns.dateTo is null")
    public List<NotificationScheduleEntity> getWaitWork();


}
