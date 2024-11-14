package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.RoleEntity;

public interface RoleService {
    RoleEntity saveRole(RoleEntity role);
    Boolean checkRole(String username);
    RoleEntity getRoleByName(String roleName);
}
