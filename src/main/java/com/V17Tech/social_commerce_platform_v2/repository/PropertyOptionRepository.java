package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.PropertyOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyOptionRepository extends JpaRepository<PropertyOption, Long> {
    PropertyOption getFirstByLabel(String label);
}
