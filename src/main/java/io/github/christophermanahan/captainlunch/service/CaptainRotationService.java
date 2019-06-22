package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CaptainRotationService implements RotationService {

    private UserRepository userRepository;

    public CaptainRotationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getHeadOfRotation() {
        return userRepository.findFirstByOrderByStartDateDesc();
    }

    public User getNextInRotation() {
        return userRepository.findFirstByOrderByEndDate();
    }
}
