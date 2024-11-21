package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.entity.PropertyEntity;
import com.V17Tech.social_commerce_platform_v2.model.PropertyDTO;
import com.V17Tech.social_commerce_platform_v2.model.PropertyOptionDTO;
import com.V17Tech.social_commerce_platform_v2.service.PostService;
import com.V17Tech.social_commerce_platform_v2.service.PropertyOptionService;
import com.V17Tech.social_commerce_platform_v2.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ManagerAppController {
    public final PropertyService propertyService;

    private final PropertyOptionService propertyOptionService;

    private final PostService postService;
    private static final Logger logger = LoggerFactory.getLogger(ManagerAppController.class);

    @PostMapping("/save")
    public ResponseEntity<?> saveProperty(@RequestBody PropertyDTO propertyDTO){
        propertyService.saveProperty(propertyDTO);
        return ResponseEntity.status(HttpStatus.OK).body("save property successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProperty(@RequestBody PropertyDTO propertyDTO, @RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.updateProperty(propertyDTO, id));
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteProperty(@RequestParam Long id){
        propertyService.deleteProperty(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete successfully");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<PropertyEntity> getById(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getById(id));
    }
    @PostMapping("/add-property-option")
    public ResponseEntity<?> addPropertyOption(@RequestBody PropertyOptionDTO payload){
        logger.info("add property option");
        return ResponseEntity.status(HttpStatus.OK).body(propertyOptionService.insertPropertyOption(payload));
    }

    @GetMapping("/delete-property-option")
    public ResponseEntity<?> deletePropertyOption(@RequestParam Long id){
        propertyOptionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("delete successfully");
    }

    @GetMapping("/verify-post")
    public ResponseEntity<?> verifyPost(@RequestHeader Long id, @RequestHeader int status){
        return ResponseEntity.status(HttpStatus.OK).body( postService.verifyPost(id, status));
    }

}
