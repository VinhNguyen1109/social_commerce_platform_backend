package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.Post;
import com.V17Tech.social_commerce_platform_v2.model.PostDTO;

import java.util.List;

public interface PostService {
    Post getPostById(Long id);

    void savePost(PostDTO postDTO, String token);

    void savePostV2(Post post);

    List<PostDTO> getPostByTitle(String title);

    PostDTO verifyPost(Long id, int status);

    List<PostDTO> getPostByKeyword(String keyword);

    Object getByVerify(String verify);
}
