package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.PropertyOption;
import com.V17Tech.social_commerce_platform_v2.model.PropertyOptionDTO;
import com.V17Tech.social_commerce_platform_v2.repository.PropertyOptionRepository;
import com.V17Tech.social_commerce_platform_v2.service.PropertyOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyOptionServiceImpl implements PropertyOptionService {
    private final PropertyOptionRepository propertyOptionRepository;
    @Override
    public PropertyOptionDTO insertPropertyOption(PropertyOptionDTO payload) {
      propertyOptionRepository.save(PropertyOption.builder()
                      .label(payload.getLabel())
                      .value(payload.getValue())
              .build());
      PropertyOption propertyOption = propertyOptionRepository.getFirstByLabel(payload.getLabel());
        return PropertyOptionDTO.builder()
                .propertyId(propertyOption.getId())
                .label(propertyOption.getLabel())
                .value(propertyOption.getValue())
                .createdAt(propertyOption.getCreatedAt())
                .updatedAt(propertyOption.getUpdatedAt())
                .build();
    }
}
