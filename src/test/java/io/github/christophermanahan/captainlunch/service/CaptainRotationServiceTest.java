package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.repository.UserRepository;
import io.github.christophermanahan.captainlunch.time.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CaptainRotationServiceTest {

    private UserRepository userRepository;
    private Time time;
    private UserRotationService rotationService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        time = mock(Time.class);
        rotationService = new CaptainRotationService(userRepository, time);
    }

    @Test
    void getsHeadOfRotation() {
        User currentCaptain = new User("W100000", new Date(Long.valueOf("0")));
        when(userRepository.findFirstByOrderByStartDateDesc()).thenReturn(currentCaptain);

        User user = rotationService.getHeadOfRotation();

        assertEquals(currentCaptain, user);
    }

    @Test
    void getsNextInRotation() {
        User nextCaptain = new User("W100000", new Date(Long.valueOf("0")));
        when(userRepository.findFirstByOrderByEndDate()).thenReturn(nextCaptain);

        User user = rotationService.getNextInRotation();

        assertEquals(nextCaptain, user);
    }

    @Test
    void rotatesCurrentCaptainToLastPositionAndNextCaptainToCurrentPosition() {
        Date twoSecondsAgo = new Date(Long.valueOf("0"));
        Date oneSecondAgo = new Date(Long.valueOf("1"));
        Date currentTime = new Date(Long.valueOf("2"));
        User currentCaptain = new User("W100000", twoSecondsAgo);
        User nextCaptain = new User("W100000", oneSecondAgo);
        when(userRepository.findFirstByOrderByStartDateDesc()).thenReturn(currentCaptain);
        when(userRepository.findFirstByOrderByEndDate()).thenReturn(nextCaptain);
        when(time.now()).thenReturn(currentTime);

        rotationService.rotate();

        assertEquals(currentTime, currentCaptain.getEndDate());
        assertEquals(currentTime, nextCaptain.getStartDate());
    }
}