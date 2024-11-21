package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.model.UserClickContactDTO;
import com.V17Tech.social_commerce_platform_v2.service.UserClickContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact-post")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactPostController {
    private final UserClickContactService userClickContactService;


    @PostMapping("/save-user-action-to-post")
    public ResponseEntity<?> saveUserActionToPost(@RequestBody UserClickContactDTO userClickContactDTO, @RequestHeader String token){
        userClickContactService.saveUserClickContact(userClickContactDTO, token);
        return ResponseEntity.status(HttpStatus.OK).body(userClickContactDTO);
    }

    @GetMapping("/get-by-title")
    public ResponseEntity<?> getPostByTitle(@RequestParam String title){
        return ResponseEntity.status(HttpStatus.OK).body(userClickContactService.getByPostTitle(title));
    }

    @GetMapping("/type-post-title")
    public ResponseEntity<?> getByTitleAndType(@RequestParam String type, @RequestParam String title){
        return ResponseEntity.status(HttpStatus.OK).body(userClickContactService.getByTypeAndPostTitle(type, title));
    }
    @GetMapping("/count-click-contact")
    public ResponseEntity<?> countClickContactOfPost(@RequestParam String postTitle){
        return ResponseEntity.status(HttpStatus.OK).body(userClickContactService.countClickContactOfPost(postTitle));
    }

    @GetMapping("/count-click-contact-type")
    public ResponseEntity<?> countClickContactOfPostByType(@RequestParam String postTitle, @RequestParam String type){
        return ResponseEntity.status(HttpStatus.OK).body(userClickContactService.countClickContactOfPostByType(postTitle, type));
    }
}
