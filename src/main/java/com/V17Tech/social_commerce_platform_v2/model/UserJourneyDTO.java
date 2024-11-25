package com.V17Tech.social_commerce_platform_v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJourneyDTO {
    private String username;
    private LinkedList<UserEvent> flow;
}
