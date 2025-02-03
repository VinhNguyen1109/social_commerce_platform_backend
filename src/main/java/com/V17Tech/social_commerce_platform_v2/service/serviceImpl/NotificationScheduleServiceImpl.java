package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationEntity;
import com.V17Tech.social_commerce_platform_v2.entity.NotificationScheduleEntity;
import com.V17Tech.social_commerce_platform_v2.model.TaskSchedulerManager;
import com.V17Tech.social_commerce_platform_v2.repository.NotificationRepository;
import com.V17Tech.social_commerce_platform_v2.repository.NotificationScheduleRepository;
import com.V17Tech.social_commerce_platform_v2.service.NotificationScheduleService;
import com.V17Tech.social_commerce_platform_v2.service.NotificationService;
import com.V17Tech.social_commerce_platform_v2.util.Status;
import com.V17Tech.social_commerce_platform_v2.util.TypeReceive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationScheduleServiceImpl implements NotificationScheduleService {
    Logger logger = LoggerFactory.getLogger(NotificationScheduleServiceImpl.class);
    private final NotificationScheduleRepository notificationScheduleRepository;
    private final  NotificationScheduleRepository scheduleRepository;
    private final NotificationRepository notificationRepository;
    private final TaskSchedulerManager   taskSchedulerManager;
    private final NotificationService notificationService;

    @Override
    public NotificationScheduleEntity createSchedule(NotificationScheduleEntity data) {
        logger.info("=============================");
        logger.info(data.getDateFrom() + "");
        data.setStatus(Status.ACTIVE.getValue());
        scheduleNotification(scheduleRepository.save(data));
        return data;
    }
    @Override
    public List<NotificationScheduleEntity> getScheduleWaitWorking() {
        return notificationScheduleRepository.findAll();
    }

    @Scheduled(fixedRate = 30000)
    public void restartAllScheduleActive() {
        if(taskSchedulerManager.hasRunningTasks()){
            logger.info("có task đang chạy");
        }else {
            NotificationScheduleEntity firstActive = notificationScheduleRepository.getFirstByStatus(Status.ACTIVE.getValue());
            if(firstActive != null){
                List<NotificationScheduleEntity> allScheduleActive = notificationScheduleRepository.getWaitWork(Status.ACTIVE.getValue());
                for (NotificationScheduleEntity schedule: allScheduleActive) {
                    scheduleNotification(schedule);
                    logger.info(schedule.getId() + "");
                }
                logger.info("có lập lịch đang chờ thực hiện");
            }else {
                logger.info("Không có lập lịch nào đang chờ thực hiện");
            }
        }
    }
    @Override
    public String cancelScheduleWithId(Long id) {
        NotificationScheduleEntity schedule = notificationScheduleRepository.getFirstById(id);
        if(schedule == null){
          return "Không tồn tại schedule với id:" + id;
        }else {
            if(schedule.getStatus() == Status.DELETE.getValue()){
                return "Schedule này đã được thực hiện";
            }else {
                taskSchedulerManager.cancelTask(id);
                schedule.setStatus(Status.DELETE.getValue());
                notificationScheduleRepository.save(schedule);
                return "xóa thành công schedule với id: " + id;
            }
        }
    }
    public void scheduleNotification(NotificationScheduleEntity data) {
        if (data != null && data.getDateFrom() != null && data.getStatus() == 1L) {
            taskSchedulerManager.scheduleTask(data.getId(), () -> {
                try {
                    performScheduledTask(data.getId());
                    logger.info("Thực hiện lịch có ID: {}", data.getId());
                    data.setDateTo(new Date());
                    notificationScheduleRepository.save(data);
                } finally {
                    taskSchedulerManager.cancelTask(data.getId());
                    data.setStatus(Status.DELETE.getValue());
                    notificationScheduleRepository.save(data);
                    logger.info("Lịch trình ID: {} đã hoàn thành và bị hủy.", data.getId());
                }
            }, data.getDateFrom());
        } else {
            logger.warn("Dữ liệu không hợp lệ hoặc lịch trình đã thực hiện.");
        }
    }
    private void performScheduledTask(Long scheduleId) {
        List<NotificationEntity> notificationEntities = notificationRepository.getByNotificationScheduleId(scheduleId);
        logger.info("số lượng noti với id lập lịch: " + notificationEntities.size());
        for (NotificationEntity notification: notificationEntities) {
            notificationService.sendNotification(notification);
        }
    }
}
