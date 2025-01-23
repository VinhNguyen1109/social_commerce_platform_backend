package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.NotificationScheduleEntity;
import com.V17Tech.social_commerce_platform_v2.repository.NotificationScheduleRepository;
import com.V17Tech.social_commerce_platform_v2.sender.KafkaSender;
import com.V17Tech.social_commerce_platform_v2.service.NotificationScheduleService;
import com.V17Tech.social_commerce_platform_v2.util.Status;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationScheduleServiceImpl implements NotificationScheduleService {
    Logger logger = LoggerFactory.getLogger(NotificationScheduleServiceImpl.class);
    private final ThreadPoolTaskScheduler taskScheduler;
    private final NotificationScheduleRepository notificationScheduleRepository;
    private final  NotificationScheduleRepository scheduleRepository;
    private final KafkaSender sender;
    private ScheduledFuture<?> scheduledTask;


    @Value("${spring.kafka.topics.create-schedule}")
    private  String clickContactTopic;
    @Override
    public NotificationScheduleEntity createSchedule(NotificationScheduleEntity data) {
        logger.info("=============================");
        logger.info(data.getDateFrom() + "");
        scheduleNotificationWithStart(data);
        data.setStatus(Status.ACTIVE.getValue());
        scheduleNotificationWithStart(data);
        return scheduleRepository.save(data);
    }

    @Override
    public List<NotificationScheduleEntity> getScheduleWaitWorking() {
        return notificationScheduleRepository.findAll();
    }
    @Override
    public void doScheduleWaitWorking() {
        List<NotificationScheduleEntity> listWaiting = notificationScheduleRepository.getWaitWork();
        for (NotificationScheduleEntity schedule: listWaiting) {
            scheduleNotificationWithStart(schedule);
        }
    }

    @Override
    public void scheduleNotificationWithStart(NotificationScheduleEntity data) {
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        logger.info("Thời gian UTC: {}", utcFormat.format(data.getDateFrom()));
        logger.info("Thời gian hệ thống: {}", data.getDateFrom());
        logger.info("Thời gian epoch: {}", data.getDateFrom().getTime());
        if (data != null && data.getDateFrom() != null) {
            Trigger startTrigger = context -> Instant.ofEpochMilli(data.getDateFrom().getTime());
            scheduledTask = taskScheduler.schedule(() -> {
                performScheduledTask();
                logger.info("Công việc đã được thực thi vào: {}", utcFormat.format(data.getDateFrom().getTime()));
                if (scheduledTask != null && !scheduledTask.isCancelled()) {
                    scheduledTask.cancel(false);
                    data.setDateTo(new Date());
                    notificationScheduleRepository.save(data);
                    logger.info("Công việc đã bị hủy sau khi thực thi.");
                }
            }, startTrigger);
        }
    }
    private void performScheduledTask() {
        logger.info("Bây giờ mới được thực hiện!!!");
    }
}
