package io.github.christophermanahan.captainlunch.schedule;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.service.UserRotationService;
import io.github.christophermanahan.captainlunch.web.Client;
import io.github.christophermanahan.captainlunch.web.slack.UserProfile;
import io.github.christophermanahan.captainlunch.web.slack.UserProfileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SchedulerTest {

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
            Collections.rotate(users, 1);
        }
    }

/*    class MockClient implements Client {
        
        public String sent;

        public HttpEntity notifyUsers(String body) {
            sent = body;
            return new HttpEntity<>(body);
        }

        public HttpEntity<User> getUserProfile(String userId) {
            UserProf
            return
        }
    }*/

    private User user1;
    private User user2;
    private User user3;
    private UserRotationService rotationService;
    private Client client;
    private Scheduler scheduler;

    @BeforeEach
    void setUp() {
        user1 = new User("W0000000", new Date());
        user2 = new User("W0000001", new Date());
        user3 = new User("W0000002", new Date());
        rotationService = new MockRotationService(List.of(user1, user2, user3));
        client = Mockito.mock(Client.class);
        scheduler = new Scheduler(rotationService, client);
    }

    @Test
    void rotatesUsers() {
        UserProfile profile = new UserProfile();
        profile.setReal_name("John Doe");
        UserProfileResponse response = new UserProfileResponse();
        response.setProfile(profile);
        Mockito.when(client.getUserProfile(user1.getIdentity())).thenReturn(new HttpEntity<>(response));
        assertEquals(user1, rotationService.getHeadOfRotation());
        assertEquals(user2, rotationService.getNextInRotation());

        scheduler.rotateAndNotify();

        assertEquals(user3, rotationService.getHeadOfRotation());
        assertEquals(user1, rotationService.getNextInRotation());
    }

    @Test
    void notifiesUsers() {
        String notification = "Test notification";

        scheduler.rotateAndNotify();

        String expected = "This week's lunch captain is: ";
    }
}
