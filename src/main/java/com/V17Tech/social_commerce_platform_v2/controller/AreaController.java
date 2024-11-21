package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.model.AreaDTO;
import com.V17Tech.social_commerce_platform_v2.service.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/area")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AreaController {
    private final AreaService areaService;

    @PostMapping("/create")
    public ResponseEntity<?> createArea(@RequestBody @Valid AreaDTO areaDTO){
        areaService.saveArea(areaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(areaDTO);
    }
}
