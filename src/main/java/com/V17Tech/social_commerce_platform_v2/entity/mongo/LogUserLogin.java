package com.V17Tech.social_commerce_platform_v2.entity.mongo;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "log-user-login")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class LogUserLogin {
    @Id
    private String id;
    private String username;
    @CreatedDate
    private Date time;
}

