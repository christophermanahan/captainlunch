package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.repository.UserRepository;
import io.github.christophermanahan.captainlunch.time.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ManualCaptainRotationService implements ManualRotationService {

    private UserRepository userRepository;
    private Time time;

    @Autowired
    public ManualCaptainRotationService(UserRepository userRepository, Time time) {
        this.userRepository = userRepository;
        this.time = time;
    }

    public void rotateIntoHead(String identity) {
        Date currentTime = time.now();
        User newHeadOfRotation = userRepository.findFirstByIdentity(identity);
        newHeadOfRotation.setStartDate(currentTime);
    }
}
