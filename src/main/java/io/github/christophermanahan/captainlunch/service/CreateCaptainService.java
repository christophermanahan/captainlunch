package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.repository.UserRepository;
import io.github.christophermanahan.captainlunch.time.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCaptainService implements CreateUserService {

    private final UserRepository userRepository;
    private Time time;

    @Autowired
    public CreateCaptainService(UserRepository userRepository, Time time) {
        this.userRepository = userRepository;
        this.time = time;
    }

    public User createUser(String identity) {
        return userRepository.save(new User(identity, time.now()));
    }
}
