package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.RoleEntity;
import com.V17Tech.social_commerce_platform_v2.repository.RoleRepository;
import com.V17Tech.social_commerce_platform_v2.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {
    private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;

    @Override
    public RoleEntity saveRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public Boolean checkRole(String username) {
        return null;
    }

    @Override
    @Cacheable(value = "user:role", key = "#roleName")
    public RoleEntity getRoleByName(String roleName) {
        logger.info("get from database: " + roleName);
        return roleRepository.getFirstByName(roleName);
    }
}
