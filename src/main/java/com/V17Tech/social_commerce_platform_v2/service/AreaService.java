package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.Area;
import com.V17Tech.social_commerce_platform_v2.model.AreaDTO;

import java.util.List;

public interface AreaService {

    void saveArea(AreaDTO areaDTO);
    Area getByName(String name);
    Area getByCode(String code);

    Area getByNameEn(String nameEn);
    public List<Area> getChildrenArea(String code);
}
