package com.V17Tech.social_commerce_platform_v2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "template")
public class TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "notification_type")
    private Long notificationType;

    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "link_website")
    private String linkWebsite;

    @Column(name = "source")
    private String source;

    @Column(name = "username")
    private String username;

    @Column(name = "user_number_phone")
    private String userNumberPhone;

    @Column(name = "user_birth_date")
    private Date userBirthdate;

    @Column(name = "otp")
    private String otp;

}
