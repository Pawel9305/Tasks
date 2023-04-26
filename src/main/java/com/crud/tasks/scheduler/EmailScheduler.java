package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;
    private static final String SUBJECT = "Tasks: once a day email";
    @Autowired
    private SimpleEmailService simpleEmailService;

    private Mail scheduledDaily() {
        long size = taskRepository.count();
        String taskOrTasks;
        if (size == 1) {
            taskOrTasks = "task";
        } else {
            taskOrTasks = "tasks";
        }
        return new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                "Currently in the database you got: " + size + "\s" + taskOrTasks,
                null
        );
    }

    @Scheduled(cron = "0 0 10 * * *")
    private void sendDaily() {
        simpleEmailService.send(scheduledDaily());
    }
}
