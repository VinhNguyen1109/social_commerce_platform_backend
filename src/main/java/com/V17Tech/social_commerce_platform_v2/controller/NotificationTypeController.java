package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationTypeEntity;
import com.V17Tech.social_commerce_platform_v2.service.NotificationTypeService;
import com.V17Tech.social_commerce_platform_v2.util.FileTextReader;
import com.V17Tech.social_commerce_platform_v2.util.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("aio/notification/type")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationTypeController {
    private final NotificationTypeService typeService;
    private final FileTextReader textReader;

    @PostMapping("/create")
    public ResultInfo<?> saveNotificationScheduleDetail1(@RequestBody NotificationTypeEntity data){
        return ResultInfo.builder()
                .status(ResultInfo.RESULT_OK)
                .data(typeService.createType(data))
                .message("ok")
                .build();
    }
    @DeleteMapping("/delete/{id}")
    public ResultInfo<?> deleteNotificationType(@PathVariable Long id){
        typeService.deleteType(id);
        return ResultInfo.builder()
                .status(ResultInfo.RESULT_OK)
                .message("delete successfully")
                .build();
    }

    @PatchMapping("/restart/{id}")
    public ResultInfo<?> restartNotificationType(@PathVariable Long id){
        typeService.restart(id);
        return ResultInfo.builder()
                .status(ResultInfo.RESULT_OK)
                .message("restart successfully")
                .build();
    }

    @GetMapping("/test")
    public String testReadFileText() throws IOException {
        String text = textReader.readFileFromResources("D:\\XinChao.txt");
        text = text.replace("{username}", "vinhnc");
        return text;
    }
}
