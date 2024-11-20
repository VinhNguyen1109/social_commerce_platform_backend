package com.V17Tech.social_commerce_platform_v2.model;

import com.V17Tech.social_commerce_platform_v2.entity.PropertyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDTO implements Serializable {

    private String name;
    private String nameEn;
    private String icon;
    private String type;

    public PropertyEntity toEntity(){
        return PropertyEntity.builder()
                .name(name)
                .nameEn(nameEn)
                .icon(icon)
                .type(type)
                .build();
    }
}
