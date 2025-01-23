package com.V17Tech.social_commerce_platform_v2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "history_send_notification")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistorySendNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "read_date")
    private Date readDate;

    @Column(name = "status")
    private int status;

    @Column(name = "chanel")
    private String chanel;

    @Column(name = "notification_receiver_id")
    private Long notificationReceiverId;

    @Column(name = "notification_id")
    private Long notificationId;
}
