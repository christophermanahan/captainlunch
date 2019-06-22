package io.github.christophermanahan.captainlunch.schedule;

import io.github.christophermanahan.captainlunch.service.UserRotationService;
import io.github.christophermanahan.captainlunch.web.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private UserRotationService rotationService;
    private Client client;

    @Autowired
    public Scheduler(UserRotationService rotationService, Client client) {
        this.rotationService = rotationService;
        this.client = client;
    }

    @Scheduled(fixedRate = 100000000)
    public void rotateAndNotify() {
    }
}
