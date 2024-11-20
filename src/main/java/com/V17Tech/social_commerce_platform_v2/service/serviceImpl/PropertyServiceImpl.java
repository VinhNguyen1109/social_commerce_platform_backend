package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.PropertyEntity;
import com.V17Tech.social_commerce_platform_v2.model.PropertyDTO;
import com.V17Tech.social_commerce_platform_v2.repository.PropertyRepository;
import com.V17Tech.social_commerce_platform_v2.service.PropertyService;
import com.V17Tech.social_commerce_platform_v2.util.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    @Override
    public void saveProperty(PropertyDTO propertyDTO) {
        propertyRepository.save(propertyDTO.toEntity());
    }

    @Override
    public PropertyEntity getById(Long id) {
       PropertyEntity property = propertyRepository.getFirstById(id);
       if(property == null) throw new BusinessException("không tồn tại property");
       return property;
    }

    @Override
    public PropertyEntity updateProperty(PropertyDTO propertyDTO, Long id) {
        PropertyEntity property = propertyRepository.getFirstById(id);
        if(property == null){
            throw new BusinessException("Không tồn tại property");
        }
        property.setName(propertyDTO.getName());
        property.setIcon(propertyDTO.getIcon());
        property.setNameEn(propertyDTO.getNameEn());
        property.setType(propertyDTO.getType());
        return propertyRepository.save(property);
    }

    @Override
    public void deleteProperty(Long id) {
        PropertyEntity property = propertyRepository.getFirstById(id);
        if(property == null){
            throw new BusinessException("Không tồn tại property");
        }
        propertyRepository.delete(property);
    }


}
