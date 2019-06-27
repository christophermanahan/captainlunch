package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.service.ManualRotationService;
import io.github.christophermanahan.captainlunch.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class OverrideCaptainController {

    private ValidationService validationService;
    private ManualRotationService rotationService;
    private Client client;

    @Autowired
    public OverrideCaptainController(ValidationService validationService, ManualRotationService rotationService, Client client) {
        this.validationService = validationService;
        this.rotationService = rotationService;
        this.client = client;
    }

    @PostMapping("/override")
    public ResponseEntity overrideCaptainRotation(
            HttpEntity<String> request,
            @RequestParam("user_id") String userId,
            @RequestParam("display_name") String displayName) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
