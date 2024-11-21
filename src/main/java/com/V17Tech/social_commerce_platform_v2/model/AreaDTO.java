package com.V17Tech.social_commerce_platform_v2.model;

import com.V17Tech.social_commerce_platform_v2.entity.Area;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaDTO {
    @NotNull(message = "code is not null")
    private String code;
    @NotNull(message = "name is not null")
    private String name;
    @NotNull(message = "nameEn is not null")
    private String nameEn;
    private String parentAreaCode;
    private String level;
    private String levelDetail;
}
