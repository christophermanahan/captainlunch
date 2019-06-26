package io.github.christophermanahan.captainlunch.schedule;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.service.UserRotationService;
import io.github.christophermanahan.captainlunch.web.Client;
import io.github.christophermanahan.captainlunch.web.slack.UserProfile;
import io.github.christophermanahan.captainlunch.web.slack.UserProfileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotificationSchedulerTest {

    class MockRotationService implements UserRotationService {

        private ArrayList<User> users;

        MockRotationService(List<User> users) {
            this.users = new ArrayList<>(users);
        }

        public User getHeadOfRotation() {
            return users.get(0);
        }

        public User getNextInRotation() {
            return users.get(1);
        }

        public void rotate() {
            Collections.rotate(users, -1);
        }
    }

    private User user1;
    private User user2;
    private User user3;
    private UserRotationService rotationService;
    private Client client;
    private NotificationScheduler notificationScheduler;

    @BeforeEach
    void setUp() {
        user1 = new User("W0000000", new Date());
        user2 = new User("W0000001", new Date());
        user3 = new User("W0000002", new Date());
        rotationService = new MockRotationService(List.of(user1, user2, user3));
        client = mock(Client.class);
        notificationScheduler = new NotificationScheduler(rotationService, client);
    }

    @Test
    void rotatesUsers() {
        UserProfile profile = new UserProfile();
        profile.setReal_name("John Doe");
        UserProfileResponse response = new UserProfileResponse();
        response.setProfile(profile);
        when(client.getUserProfile(rotationService.getNextInRotation().getIdentity())).thenReturn(new HttpEntity<>(response));
        assertEquals(user1, rotationService.getHeadOfRotation());
        assertEquals(user2, rotationService.getNextInRotation());

        notificationScheduler.rotateAndNotify();

        assertEquals(user2, rotationService.getHeadOfRotation());
        assertEquals(user3, rotationService.getNextInRotation());
    }

    @Test
    void notifiesUsers() {
        String name = "John Doe";
        UserProfile profile = new UserProfile();
        profile.setReal_name(name);
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setProfile(profile);
        when(client.getUserProfile(rotationService.getNextInRotation().getIdentity())).thenReturn(new HttpEntity<>(userProfileResponse));
        String notification = "This week's lunch captain is " + name;
        String responseBody = "OK";
        when(client.notifyUsers(notification)).thenReturn(new HttpEntity<>(responseBody));

        HttpEntity notificationResponse =  notificationScheduler.rotateAndNotify();

        assertEquals(responseBody, notificationResponse.getBody());
    }
}
