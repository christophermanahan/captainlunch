package io.github.christophermanahan.captainlunch.schedule;

import io.github.christophermanahan.captainlunch.service.AutomatedRotationService;
import io.github.christophermanahan.captainlunch.web.Client;
import io.github.christophermanahan.captainlunch.web.slack.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    private AutomatedRotationService rotationService;
    private Client client;

    @Autowired
    public NotificationScheduler(AutomatedRotationService rotationService, Client client) {
        this.rotationService = rotationService;
        this.client = client;
    }

    @Scheduled(cron = "${app.captainRotationNotificationSchedule}")
    public HttpEntity rotateAndNotify() {
        rotationService.rotate();
        HttpEntity<UserProfileResponse> currentCaptain = client.getUserProfile(rotationService.getHeadOfRotation().getIdentity());
        String notification = "This week's lunch captain is " + currentCaptain.getBody().getProfile().getReal_name();
        return client.notifyUsers(notification);
    }
}
