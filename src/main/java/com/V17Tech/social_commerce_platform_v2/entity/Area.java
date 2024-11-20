package com.V17Tech.social_commerce_platform_v2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "area")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Area {
    @Id
    @Column(name = "code", length = 16)
    private String code;
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    @Column(name = "name_en", nullable = false, length = 128)
    private String nameEn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code")
    private Area parentArea;
    @OneToMany(mappedBy = "parentArea")
    private List<Area> childrenAreaList;
    @Column(name = "level", length = 16)
    private String level;
    @Column(name = "level_detail", length = 64)
    private String levelDetail;
    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;
    @Column(name = "update_at")
    @LastModifiedDate
    private Date updatedAt;
}
