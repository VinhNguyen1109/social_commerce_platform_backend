package com.V17Tech.social_commerce_platform_v2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("check")
  //  @PreAuthorize("hasRole('client_user')")
    public String checkExpireIn(){
        return "ok";
    }

}
