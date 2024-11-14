package com.V17Tech.social_commerce_platform_v2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "properties")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Property {
    @Id
    @Column(name = "id", length = 64)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 64, nullable = false)
    private String name;
    @Column(name = "name_en", length = 64)
    private String nameEn;
    @Column(name = "icon", length = 1024, nullable = false)
    private String icon;
    @Column(name = "type", length = 16)
    private String type;
    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;
    @Column(name = "update_at")
    private Date updateAt;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<PropertyOption> propertyOptionList;

}
