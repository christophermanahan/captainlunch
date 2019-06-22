package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.repository.UserRepository;
import io.github.christophermanahan.captainlunch.time.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CaptainRotationService implements UserRotationService {

    private UserRepository userRepository;
    private Time time;

    @Autowired
    public CaptainRotationService(UserRepository userRepository, Time time) {
        this.userRepository = userRepository;
        this.time = time;
    }

    public User getHeadOfRotation() {
        return userRepository.findFirstByOrderByStartDateDesc();
    }

    public User getNextInRotation() {
        return userRepository.findFirstByOrderByEndDate();
    }

    public void rotate() {
        Date currentTime = time.now();
        getHeadOfRotation().setEndDate(currentTime);
        getNextInRotation().setStartDate(currentTime);
    }
}
