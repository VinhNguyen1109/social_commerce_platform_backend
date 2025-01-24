package com.V17Tech.social_commerce_platform_v2.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class TaskSchedulerManager {

    private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerManager.class);

    private final TaskScheduler taskScheduler;
    private final Map<Long, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();

    public TaskSchedulerManager() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.initialize();
        this.taskScheduler = scheduler;
    }

    public void scheduleTask(Long taskId, Runnable runnable, Date startTime) {
        if (taskMap.containsKey(taskId)) {
            logger.warn("Task với ID {} đã tồn tại.", taskId);
            return;
        }
        ScheduledFuture<?> future = taskScheduler.schedule(runnable, triggerContext -> Instant.ofEpochMilli(startTime.getTime()));
        taskMap.put(taskId, future);
        logger.info("Task với ID {} đã được thêm vào lịch trình.", taskId);
    }

    public void cancelTask(Long taskId) {
        ScheduledFuture<?> future = taskMap.get(taskId);
        if (future != null) {
            future.cancel(false);
            taskMap.remove(taskId);
            logger.info("Task với ID {} đã bị hủy.", taskId);
        } else {
            logger.warn("Không tìm thấy task với ID {}.", taskId);
        }
    }

    public boolean isTaskRunning(Long taskId) {
        ScheduledFuture<?> future = taskMap.get(taskId);
        return future != null && !future.isCancelled();
    }
    public boolean hasRunningTasks() {
        return taskMap.values().stream().anyMatch(future -> !future.isCancelled() && !future.isDone());
    }

    public Map<Long, ScheduledFuture<?>> getRunningTasks() {
        return taskMap;
    }
}
