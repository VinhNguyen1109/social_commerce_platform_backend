package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.model.PostDTO;
import com.V17Tech.social_commerce_platform_v2.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @PostMapping("/create-post")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostDTO postDTO, @RequestHeader String author){
        postService.savePost(postDTO, author);
        return ResponseEntity.status(HttpStatus.OK).body(postDTO);
    }

    @PostMapping("/get-by-title")
    public ResponseEntity<?> getByTitle( @RequestHeader String title){
        return ResponseEntity.status(HttpStatus.OK).body( postService.getPostByTitle(title));
    }
}
