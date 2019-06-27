package io.github.christophermanahan.captainlunch.schedule;

import io.github.christophermanahan.captainlunch.service.RotationService;
import io.github.christophermanahan.captainlunch.web.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    private RotationService rotationService;
    private Client client;

    @Autowired
    public NotificationScheduler(RotationService rotationService, Client client) {
        this.rotationService = rotationService;
        this.client = client;
    }

    @Scheduled(cron = "${app.captainRotationNotificationSchedule}")
    public HttpEntity rotateAndNotify() {
        rotationService.rotate();
        String notification = "This week's lunch captain is " + getCurrentCaptainName();
        return client.notifyUsers(notification);
    }

    private String getCurrentCaptainName() {
        String currentCaptainIdentity = rotationService.getHeadOfRotation().getIdentity();
        return client.getUserProfile(currentCaptainIdentity).getReal_name();
    }
}
