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

public class ManualCaptainRotationServiceTest {

    private UserRepository userRepository;
    private Time time;
    private ManualCaptainRotationService rotationService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        time = mock(Time.class);
        rotationService = new ManualCaptainRotationService(userRepository, time);
    }

    @Test
    void startsTheOverridingCaptainsRotation() {
        Date twoSecondsAgo = new Date(Long.valueOf("0"));
        Date oneSecondAgo = new Date(Long.valueOf("1"));
        Date currentTime = new Date(Long.valueOf("2"));
        String identity = "W100000";
        User currentCaptain = new User(identity, twoSecondsAgo);
        User newHead = new User("W100000", oneSecondAgo);
        when(userRepository.findFirstByOrderByStartDateDesc()).thenReturn(currentCaptain);
        when(userRepository.findFirstByIdentity(identity)).thenReturn(newHead);
        when(time.now()).thenReturn(currentTime);

        rotationService.rotateIntoHead(identity);

        assertEquals(currentTime, newHead.getStartDate());
    }
}
