package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.entity.UserJourneyEntity;
import com.V17Tech.social_commerce_platform_v2.model.UserEvent;
import com.V17Tech.social_commerce_platform_v2.repository.UserJourneyRepository;
import com.V17Tech.social_commerce_platform_v2.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedList;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final UserJourneyRepository userJourneyRepository;
    @GetMapping("check")
  //  @PreAuthorize("hasRole('client_user')")
    public String checkExpireIn(){
        return "ok";
    }

    @GetMapping("/save-linkedlist")
    public LinkedList<?> saveLinkedList() throws IOException {
        LinkedList<UserEvent> result = new LinkedList<>();
//        result.add(UserJourneyDTO.builder().userId(1L)
//                        .screen("home")
//                .build());
//        result.add(UserJourneyDTO.builder().userId(1L)
//                .screen("pay")
//                .build());
        //System.out.println(CommonUtil.serializeToString(result));
        String data = CommonUtil.serializeToString(result);
        System.out.println(data);
        UserJourneyEntity save = UserJourneyEntity.builder()
                .id(1L)
                .data(CommonUtil.serializeToString(result))
                .build();
        userJourneyRepository.save(save);
        return result;
    }



    @GetMapping("/get-linkedlist")
    public LinkedList<?> getLinkedList() throws IOException, ClassNotFoundException {
        UserJourneyEntity data = userJourneyRepository.getReferenceById(1L);
        LinkedList<Long> result = (LinkedList<Long>) CommonUtil.deserializeFromString(data.getData(), LinkedList.class);
        return result;
    }

}
