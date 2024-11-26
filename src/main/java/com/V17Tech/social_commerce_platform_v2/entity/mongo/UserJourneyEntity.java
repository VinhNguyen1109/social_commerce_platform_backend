package com.V17Tech.social_commerce_platform_v2.entity.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Builder
@NoArgsConstructor
@Data
@Document(collection = "user-journey-flow")
@EntityListeners(AuditingEntityListener.class)
public class UserJourneyEntity {
    @Id
    private String id;
    private String username;
    private String sessionId;
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date time;
    private String screenName;
    private String preScreenName;
    private int status;
}
