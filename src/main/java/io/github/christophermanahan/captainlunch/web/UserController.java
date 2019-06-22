package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.service.CreateUserService;
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
public class UserController {

    private CreateUserService userService;
    private ValidationService validationService;

    @Autowired
    public UserController(CreateUserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> createUser(
            HttpEntity<String> request,
            @RequestParam("user_id") String userId,
            @RequestParam("user_name") String userName) {
        if (validationService.validateRequest(request)) {
            userService.createUser(userId);
            return createdResponse(userName);
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

    private ResponseEntity<String> createdResponse(@RequestParam("user_name") String userName) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(String.format("Ahoy Captain %s! You have joined the lunch rotation.", userName));
    }
}
