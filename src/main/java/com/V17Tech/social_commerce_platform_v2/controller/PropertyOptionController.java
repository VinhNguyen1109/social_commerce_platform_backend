package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.model.PropertyOptionDTO;
import com.V17Tech.social_commerce_platform_v2.service.PropertyOptionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property-option")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyOptionController {
    private final PropertyOptionService propertyOptionService;
    private static final Logger logger = LoggerFactory.getLogger(PropertyOptionController.class);
    @PostMapping("/add-property-option")
    public ResponseEntity<?> addPropertyOption(@RequestBody PropertyOptionDTO payload){
        logger.info("add property option");
        return ResponseEntity.status(HttpStatus.OK).body(propertyOptionService.insertPropertyOption(payload));
    }
}
