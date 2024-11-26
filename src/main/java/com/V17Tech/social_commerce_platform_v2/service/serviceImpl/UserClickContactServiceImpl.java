package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.UserClickContact;
import com.V17Tech.social_commerce_platform_v2.model.UserClickContactDTO;
import com.V17Tech.social_commerce_platform_v2.repository.UserClickContactRepository;
import com.V17Tech.social_commerce_platform_v2.sender.KafkaSender;
import com.V17Tech.social_commerce_platform_v2.service.AccountService;
import com.V17Tech.social_commerce_platform_v2.service.PostService;
import com.V17Tech.social_commerce_platform_v2.service.UserClickContactService;
import com.V17Tech.social_commerce_platform_v2.util.CommonUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserClickContactServiceImpl implements UserClickContactService {

    private final UserClickContactRepository userClickContactRepository;

    private final AccountService accountService;
    private final PostService postService;

    private final KafkaSender sender;

    @Value("${spring.kafka.topics.post-click-contact}")
    private  String clickContactTopic;

    @Override
    public void saveUserClickContact(UserClickContactDTO userClickContactDTO, String token) {
        String username = CommonUtil.getUserNameFromToken(token);
        UserClickContact userClickContact = UserClickContact.builder()
                .user(accountService.getFirstByUsername(username))
                .post(postService.getPostById(userClickContactDTO.getPostId()))
                .type(userClickContactDTO.getType())
                .build();
        sender.send(clickContactTopic, userClickContactDTO);

        userClickContactRepository.save(userClickContact);
    }


    @Override
    public List<UserClickContact> getByPostTitle(String title) {
        return userClickContactRepository.getByPostTitle(title);
    }

    @Override
    public List<UserClickContact> getByUserId(Long id) {
        return userClickContactRepository.getByUserId(id);
    }

    @Override
    public List<UserClickContact> getByType(String type) {
        return userClickContactRepository.getByType(type);
    }

    @Override
    public UserClickContact getByTypeAndPostTitle(String type, String title) {
        return userClickContactRepository.getByTypeAndPostTitle(type, title);
    }

    @Override
    public Long countClickContactOfPost(String postTitle) {
        return userClickContactRepository.countByPostTitle(postTitle);
    }

    @Override
    public Long countClickContactOfPostByType(String postTilte, String type) {
        return userClickContactRepository.countByPostTitleAndType(postTilte, type);
    }

}
