package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.model.PostDTO;
import com.V17Tech.social_commerce_platform_v2.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/get-by-keyword")
    public ResponseEntity<?> getPostByKeyword(@RequestHeader String keyword){
        return ResponseEntity.status(HttpStatus.OK).body( postService.getPostByKeyword(keyword.trim()));
    }

    @GetMapping("/get-by-status")
    public ResponseEntity<?> getPostByVerify(@RequestHeader String verify){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getByVerify(verify));
    }

    @GetMapping("/get-top3-ranking")
    public ResponseEntity<?> getTop3Ranking(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getTop3Ranking());
    }

    @PostMapping("/get-posts")
    public ResponseEntity<Page<PostDTO>> getPosts(@RequestHeader (defaultValue = "0") String page,
                                                  @RequestHeader (defaultValue = "5") String size,
                                                  @RequestHeader (defaultValue = "createdAt,desc")String sort){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPosts(page, size, sort));
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getPostById(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
    }
}
