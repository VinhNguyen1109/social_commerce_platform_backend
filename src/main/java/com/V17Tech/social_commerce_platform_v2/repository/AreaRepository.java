package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    Area getFirstByName(String name);

    Area getFirstByCode(String code);

    Area getFirstByNameEn(String nameEn);

    @Query(value = "select a.childrenAreaList from Area a where a.code = :code")
    List<Area> getChildrenArea(String code);
}
