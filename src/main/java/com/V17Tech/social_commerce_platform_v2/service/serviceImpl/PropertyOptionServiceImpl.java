package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.PropertyOptionEntity;
import com.V17Tech.social_commerce_platform_v2.model.PropertyOptionDTO;
import com.V17Tech.social_commerce_platform_v2.repository.PropertyOptionRepository;
import com.V17Tech.social_commerce_platform_v2.repository.PropertyRepository;
import com.V17Tech.social_commerce_platform_v2.service.PropertyOptionService;
import com.V17Tech.social_commerce_platform_v2.util.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyOptionServiceImpl implements PropertyOptionService {
    private final PropertyOptionRepository propertyOptionRepository;

    private final PropertyRepository propertyRepository;
    @Override
    public PropertyOptionDTO insertPropertyOption(PropertyOptionDTO payload) {
      propertyOptionRepository.save(PropertyOptionEntity.builder()
                      .property(propertyRepository.getFirstById(payload.getPropertyId()))
                      .label(payload.getLabel())
                      .value(payload.getValue())
              .build());
      PropertyOptionEntity propertyOption = propertyOptionRepository.getFirstByLabel(payload.getLabel());
        return PropertyOptionDTO.builder()
                .propertyId(propertyOption.getId())
                .label(propertyOption.getLabel())
                .value(propertyOption.getValue())
                .createdAt(propertyOption.getCreatedAt())
                .updatedAt(propertyOption.getUpdatedAt())
                .propertyId(propertyOption.getProperty().getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        PropertyOptionEntity propertyOption = propertyOptionRepository.getFirstById(id);
        if(propertyOption == null) throw new BusinessException("Không tồn tại property option entity");
        propertyOptionRepository.delete(propertyOption);
    }


}
