package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.service.CreateUserService;
import io.github.christophermanahan.captainlunch.service.RotationService;
import io.github.christophermanahan.captainlunch.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlackController {

    private CreateUserService userService;
    private ValidationService validationService;
    private RotationService rotationService;
    private Client client;

    @Autowired
    public SlackController(CreateUserService userService, ValidationService validationService, RotationService rotationService, Client client) {
        this.userService = userService;
        this.validationService = validationService;
        this.rotationService = rotationService;
        this.client = client;
    }

    @PostMapping("/override")
    public ResponseEntity overrideCaptainRotation(
            HttpEntity<String> request,
            @RequestParam("user_id") String userId) {
        if (validationService.validateRequest(request)) {
            rotationService.rotateIntoHead(userId);
            String displayName = client.getUserProfile(userId).getReal_name();
            client.notifyUsers(displayName + " overrode the rotation and is your new lunch captain!");
            return okResponse();
        } else {
            return unauthorizedResponse();
        }
    }

    private ResponseEntity<Object> okResponse() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/join")
    public ResponseEntity<String> createUser(
            HttpEntity<String> request,
            @RequestParam("user_id") String userId) {
        if (validationService.validateRequest(request)) {
            userService.createUser(userId);
            String displayName = client.getUserProfile(userId).getReal_name();
            return createdResponse(displayName);
        } else {
            return unauthorizedResponse();
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> userAlreadyExists() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Ahoy captain! You are already a captain.");
    }

    private ResponseEntity<String> unauthorizedResponse() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized");
    }

    private ResponseEntity<String> createdResponse(String displayName) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Ahoy Captain" + displayName + "! You have joined the lunch rotation.");
    }
}
