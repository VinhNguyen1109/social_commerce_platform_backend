package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.UserClickContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClickContactRepository extends JpaRepository<UserClickContact, Long> {
    UserClickContact getFirstById(Long id);

    @Query(value = "select ulc from UserClickContact ulc where ulc.post.title = :title")
    List<UserClickContact> getByPostTitle(String title);

    @Query(value = "select ulc from UserClickContact ulc where ulc.user.id = :id")
    List<UserClickContact> getByUserId(Long id);

    List<UserClickContact> getByType(String type);

    @Query(value = "select ulc from UserClickContact ulc where ulc.type = :type and ulc.post.title = :title")
    UserClickContact getByTypeAndPostTitle(String type, String title);

    @Query(value = "select count(ulc) from  UserClickContact ulc where ulc.post.title = :title")
    Long countByPostTitle(String title);

    @Query(value = "select count(ulc) from UserClickContact  ulc where ulc.post.title = :postTitle and ulc.type = :type")
    Long countByPostTitleAndType(String postTitle, String type);
}
