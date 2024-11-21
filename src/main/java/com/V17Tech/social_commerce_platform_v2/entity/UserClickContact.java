package com.V17Tech.social_commerce_platform_v2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "user_click_contact")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserClickContact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 64)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "type", length = 32)
    private String type;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

}
