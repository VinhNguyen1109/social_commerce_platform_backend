package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.PropertyOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyOptionRepository extends JpaRepository<PropertyOptionEntity, Long> {
    PropertyOptionEntity getFirstByLabel(String label);
    PropertyOptionEntity getFirstById(Long id);
}
