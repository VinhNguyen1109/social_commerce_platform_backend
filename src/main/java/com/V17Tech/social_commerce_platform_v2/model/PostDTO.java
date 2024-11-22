package com.V17Tech.social_commerce_platform_v2.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostDTO {
    private Long id;
    @NotNull(message = "title is not null")
    private String title;

    private String subTitle;

    private String description;

    private String address;

    private String wardCode;

    private String districtCode;

    private String provinceCode;

    private String username;

    private String phoneNumber;

    private String zalo;

    private String email;

    private String type;

    private String estateType;

    private int fromPrice;

    private int toPrice;

    private String priceType;

    private String constructionArea;

    private int numBedrooms;

    private int numToilets;

    private int numFloors;

    private String images;

    private String videos;

    private String status;

    private String positions;

    private Date visibleFrom;

    private Date visibleTo;

    private int numOfView;

    private int numOfShare;

    private int numOfLike;

    private int numOfClickContact;

    private int verified;

    private Double rankingPoint;

}
