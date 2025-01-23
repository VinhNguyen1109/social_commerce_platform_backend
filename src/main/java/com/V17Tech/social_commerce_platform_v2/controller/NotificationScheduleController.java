package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationScheduleEntity;
import com.V17Tech.social_commerce_platform_v2.service.NotificationScheduleService;
import com.V17Tech.social_commerce_platform_v2.util.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("aio/notification/schedule")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationScheduleController {
    private final NotificationScheduleService notificationScheduleService;

    @PostMapping("/create-schedule")
    public ResultInfo<?> saveNotificationScheduleDetail1(@RequestBody NotificationScheduleEntity data){
        notificationScheduleService.createSchedule(data);
        return ResultInfo.builder()
                .status(ResultInfo.RESULT_OK)
                .data(data)
                .message("ok")
                .build();
    }

    @GetMapping("/do-schedule")
    public ResultInfo<?> scheduleNotification(){
        notificationScheduleService.doScheduleWaitWorking();
        return ResultInfo.builder()
                .status(ResultInfo.RESULT_OK)
                .message("ok")
                .build();
    }
}
