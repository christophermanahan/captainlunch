package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.repository.UserRepository;
import io.github.christophermanahan.captainlunch.time.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateCaptainCandidateServiceTest {

    private UserRepository userRepository;
    private Time time;
    private CreateCaptainCandidateService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        time = mock(Time.class);
        userService = new CreateCaptainCandidateService(userRepository, time);
    }

    @Test
    void createsUserSuccessfully() {
        String identity = "W100000";
        Date currentTime = new Date(Long.valueOf("0"));
        when(time.now()).thenReturn(currentTime);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        User created = userService.createUser(identity);

        assertEquals(identity, created.getIdentity());
        assertEquals(currentTime, created.getStartDate());
        assertEquals(currentTime, created.getEndDate());
    }
}