package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post getFirstById(Long id);

    List<Post> getPostByTitle(String title);

}
