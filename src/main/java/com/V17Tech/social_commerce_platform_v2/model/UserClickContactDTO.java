package com.V17Tech.social_commerce_platform_v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserClickContactDTO {

    private Long postId;

    private String type;
}
