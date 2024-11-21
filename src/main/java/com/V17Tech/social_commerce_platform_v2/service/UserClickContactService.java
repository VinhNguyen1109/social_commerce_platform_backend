package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.UserClickContact;
import com.V17Tech.social_commerce_platform_v2.model.UserClickContactDTO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserClickContactService {
    void saveUserClickContact(UserClickContactDTO userClickContactDTO, String token);

    List<UserClickContact> getByPostTitle(String title);

    List<UserClickContact> getByUserId(Long id);

    List<UserClickContact> getByType(String type);

    UserClickContact getByTypeAndPostTitle(String type, String title);

    Long countClickContactOfPost(String postTitle);

    Long countClickContactOfPostByType(String postTilte, String type);
}
