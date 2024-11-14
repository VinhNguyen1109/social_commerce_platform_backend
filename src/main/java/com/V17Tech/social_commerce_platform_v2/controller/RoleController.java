package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.annotation.Permission;
import com.V17Tech.social_commerce_platform_v2.entity.RoleEntity;
import com.V17Tech.social_commerce_platform_v2.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/add-role")
    public ResponseEntity<?> saveRole(@RequestBody RoleEntity role){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.saveRole(role));
    }
    @GetMapping("/check")
    @Permission(role = "client_admin")
    public ResponseEntity<?> getRoleByName(){
        if(roleService.getRoleByName("client_user") == null)
            return ResponseEntity.status(HttpStatus.OK).body("null");
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

}
