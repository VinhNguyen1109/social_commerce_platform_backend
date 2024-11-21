package com.V17Tech.social_commerce_platform_v2.entity;

import com.V17Tech.social_commerce_platform_v2.model.PostDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 64)
    private Long id;
    @Column(name = "title", length = 1024, nullable = false)
    private String title;

    @Column(name = "sub_title", length = 1024)
    private String subTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "address", length = 1024)
    private String address;

    @ManyToOne
    @JoinColumn(name = "ward_code")
    private Area ward;

    @ManyToOne
    @JoinColumn(name = "district_code")
    private Area district;

    @ManyToOne
    @JoinColumn(name = "province_code")
    private Area province;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountEntity account;

    @Column(name = "phone_number", length = 16)
    private String phoneNumber;
    @Column(name = "zalo", length = 64)
    private String zalo;
    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "type", length = 32)
    private String type;

    @Column(name = "estate_type", length = 64)
    private String estateType;

    @Column(name = "from_price", nullable = false)
    private int fromPrice;

    @Column(name = "to_price", nullable = false)
    private int toPrice;

    @Column(name = "price_type", length = 8)
    private String priceType;

    @Column(name = "construction_area")
    private String constructionArea;

    @Column(name = "num_bedrooms")
    private int numBedrooms;

    @Column(name = "num_toilets")
    private int numToilets;

    @Column(name = "num_floors")
    private int numFloors;

    @Column(name = "images", columnDefinition = "text")
    private String images;

    @Column(name = "videos", columnDefinition = "text")
    private String videos;

    @Column(name = "status", length = 32)
    private String status;

    @Column(name = "positions", length = 32)
    private String positions;

    @Column(name = "visible_from")
    private Date visibleFrom;

    @Column(name = "visible_to")
    private Date visibleTo;

    @Column(name = "num_of_view")
    private int numOfView;

    @Column(name = "num_of_share")
    private int numOfShare;

    @Column(name = "num_of_like")
    private int numOfLike;

    @Column(name = "num_of_click_contact")
    private int numOfClickContact;

    @Column(name = "verified", columnDefinition = "tinyint")
    private int verified;

    @Column(name = "ranking_point")
    private Double rankingPoint;

    @Column(name = "created_by", length = 64)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by", length = 64)
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    public PostDTO toDTO(){
        return PostDTO.builder()
                .title(title)
                .subTitle(subTitle)
                .description(description)
                .address(address)
                .wardCode(ward.getCode())
                .districtCode(district.getCode())
                .provinceCode(province.getCode())
                .username(account.getUsername())
                .phoneNumber(phoneNumber)
                .zalo(zalo)
                .email(email)
                .type(type)
                .estateType(estateType)
                .fromPrice(fromPrice)
                .toPrice(toPrice)
                .priceType(priceType)
                .constructionArea(constructionArea)
                .numBedrooms(numBedrooms)
                .numFloors(numFloors)
                .numToilets(numToilets)
                .numOfView(numOfView)
                .numOfShare(numOfShare)
                .numOfLike(numOfLike)
                .numOfClickContact(numOfClickContact)
                .verified(verified)
                .rankingPoint(rankingPoint)
                .build();
    }
}
