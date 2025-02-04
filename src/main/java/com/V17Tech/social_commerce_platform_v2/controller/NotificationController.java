package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;
import com.V17Tech.social_commerce_platform_v2.service.NotificationService;
import com.V17Tech.social_commerce_platform_v2.service.serviceImpl.RoleServiceImpl;
import com.V17Tech.social_commerce_platform_v2.util.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("aio/notification")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationController {
    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);


    @PostMapping("/create")
    public ResultInfo<?> createNotification(@RequestBody NotificationEntity data){
        logger.info(data.toString());
        return ResultInfo.builder()
                .data(notificationService.createNotification(data))
                .message("ok")
                .status(ResultInfo.RESULT_OK)
                .build();
    }
    @GetMapping("/send")
    public ResultInfo<?> sendNotification(@RequestBody NotificationEntity data){
        notificationService.sendNotification(data);
        //notificationService.sendNotificationV2(data);
        return ResultInfo.builder()
                .message("ok")
                .status(ResultInfo.RESULT_OK)
                .build();
    }

}
