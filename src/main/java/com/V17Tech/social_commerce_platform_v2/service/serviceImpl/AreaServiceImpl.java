package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.Area;
import com.V17Tech.social_commerce_platform_v2.model.AreaDTO;
import com.V17Tech.social_commerce_platform_v2.repository.AreaRepository;
import com.V17Tech.social_commerce_platform_v2.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Override
    public void saveArea(AreaDTO areaDTO) {
        Area parent ;
        if(areaDTO.getParentAreaCode() == null){
            parent = null;
        }else {
            parent = areaRepository.getFirstByCode(areaDTO.getCode());
        }
        areaRepository.save(Area.builder()
                        .level(areaDTO.getLevel())
                        .levelDetail(areaDTO.getLevelDetail())
                        .code(areaDTO.getCode())
                        .name(areaDTO.getName())
                        .nameEn(areaDTO.getNameEn())
                        .parentArea(parent)
                .build());
    }

    @Override
    public Area getByName(String name) {
        return areaRepository.getFirstByName(name);
    }

    @Override
    public Area getByCode(String code) {
        return areaRepository.getFirstByCode(code);
    }

    @Override
    public Area getByNameEn(String nameEn) {
        return null;
    }

    public List<Area> getChildrenArea(String code){
        return areaRepository.getChildrenArea(code);
    }

}
