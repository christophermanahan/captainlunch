package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Mockito.when;

class RotationServiceTest {

    private UserRepository userRepository;
    private RotationService rotationService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        rotationService = new CaptainRotationService(userRepository);
    }

    @Test
    void getsHeadOfRotation() {
        User currentCaptain = new User("W100000",new Date(Long.valueOf("0")));
        when(userRepository.findFirstByOrderByStartDateDesc()).thenReturn(currentCaptain);

        User user = rotationService.getHeadOfRotation();

        Assertions.assertEquals(currentCaptain, user);
    }

    @Test
    void getsNextInRotation() {
        User nextCaptain = new User("W100000",new Date(Long.valueOf("0")));
        when(userRepository.findFirstByOrderByEndDate()).thenReturn(nextCaptain);

        User user = rotationService.getNextInRotation();

        Assertions.assertEquals(nextCaptain, user);
    }
}