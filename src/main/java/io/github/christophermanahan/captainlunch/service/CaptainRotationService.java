package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.repository.UserRepository;
import io.github.christophermanahan.captainlunch.time.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CaptainRotationService implements RotationService {

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
        moveToEnd(getHeadOfRotation(), currentTime);
        moveToHead(getNextInRotation(), currentTime);
    }

    public void rotateIntoHead(String identity) {
        Date currentTime = time.now();
        User newHeadOfRotation = userRepository.findFirstByIdentity(identity);
        moveToHead(newHeadOfRotation, currentTime);
    }

    private void moveToEnd(User user, Date currentTime) {
        user.setEndDate(currentTime);
        userRepository.save(user);
    }

    private void moveToHead(User user, Date currentTime) {
        user.setStartDate(currentTime);
        userRepository.save(user);
    }
}
