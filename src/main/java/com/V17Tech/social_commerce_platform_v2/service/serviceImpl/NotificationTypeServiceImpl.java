package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationTypeEntity;
import com.V17Tech.social_commerce_platform_v2.repository.NotificationTypeRepository;
import com.V17Tech.social_commerce_platform_v2.service.NotificationTypeService;
import com.V17Tech.social_commerce_platform_v2.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationTypeServiceImpl implements NotificationTypeService {

    private final NotificationTypeRepository typeRepository;
    @Override
    public NotificationTypeEntity createType(NotificationTypeEntity data) {
        data.setStatus(Status.ACTIVE.getValue());
        return typeRepository.save(data);
    }

    @Override
    public void deleteType(Long id) {
        NotificationTypeEntity type = typeRepository.getFirstById(id);
        type.setStatus(Status.DELETE.getValue());
        typeRepository.save(type);
    }

    @Override
    public void restart(Long id) {
        NotificationTypeEntity type = typeRepository.getFirstById(id);
        type.setStatus(Status.ACTIVE.getValue());
        typeRepository.save(type);
    }

}
