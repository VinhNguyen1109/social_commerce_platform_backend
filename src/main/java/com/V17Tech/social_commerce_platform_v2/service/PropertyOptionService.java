package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.model.PropertyOptionDTO;

public interface PropertyOptionService {
    PropertyOptionDTO insertPropertyOption(PropertyOptionDTO propertyOption);
    void delete(Long id);
}
