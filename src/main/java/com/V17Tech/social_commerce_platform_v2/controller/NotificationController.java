package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;
import com.V17Tech.social_commerce_platform_v2.service.NotificationService;
import com.V17Tech.social_commerce_platform_v2.util.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("aio/notification")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/create")
    public ResultInfo<?> createNotification(@RequestBody NotificationEntity data){
        return ResultInfo.builder()
                .data(notificationService.createNotification(data))
                .message("ok")
                .status(ResultInfo.RESULT_OK)
                .build();
    }
    @GetMapping("/send")
    public ResultInfo<?> sendNotification(@RequestBody NotificationEntity data){
        notificationService.sendNotification(data);
        return ResultInfo.builder()
                .message("ok")
                .status(ResultInfo.RESULT_OK)
                .build();
    }

}
