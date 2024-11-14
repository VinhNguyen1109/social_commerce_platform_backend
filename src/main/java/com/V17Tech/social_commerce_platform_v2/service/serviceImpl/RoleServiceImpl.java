package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.RoleEntity;
import com.V17Tech.social_commerce_platform_v2.repository.RoleRepository;
import com.V17Tech.social_commerce_platform_v2.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {
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
    public RoleEntity getRoleByName(String roleName) {
        return roleRepository.getFirstByName(roleName);
    }
}
