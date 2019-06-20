package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.service.UserService;
import io.github.christophermanahan.captainlunch.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;
    private ValidationService validationService;

    @Autowired
    public UserController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> createUser(
            HttpEntity<String> request,
            @RequestParam("user_id") String userId,
            @RequestParam("user_name") String userName) {
        userService.createUser(userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(String.format("Ahoy Captain %s! You have joined the lunch rotation.", userName));
    }
}
