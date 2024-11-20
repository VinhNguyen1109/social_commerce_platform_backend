package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.PropertyEntity;
import com.V17Tech.social_commerce_platform_v2.model.PropertyDTO;

public interface PropertyService {
    void saveProperty(PropertyDTO propertyDTO);
    PropertyEntity getById(Long id);

    PropertyEntity updateProperty(PropertyDTO propertyDTO, Long id);

    void deleteProperty(Long id);
}
