package io.github.christophermanahan.captainlunch.web.slack;

import io.github.christophermanahan.captainlunch.configuration.OutgoingRequestConfiguration;
import io.github.christophermanahan.captainlunch.web.Client;
import io.github.christophermanahan.captainlunch.web.RequestCreator;
import io.github.christophermanahan.captainlunch.web.Sender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SlackClientTest {

    private Sender sender;
    private RequestCreator requestCreator;
    private OutgoingRequestConfiguration configuration;
    private Client client;

    @BeforeEach
    void setUp() {
        sender = mock(Sender.class);
        requestCreator = mock(RequestCreator.class);
        configuration = mock(OutgoingRequestConfiguration.class);
        client = new SlackClient(sender, requestCreator, configuration);
    }

    @Test
    void itSendsAnAuthorizedPostRequestToTheConfiguredLocation() {
        String uri = "https://hooks.slack.com/services/test_uri";
        String authToken = "xoxp-test-auth-token";
        String notification = "Hello Lunch Captain!";
        HttpEntity slackRequest = new HttpEntity<>(new SlackMessage(notification));
        HttpEntity slackResponse = new HttpEntity<>(new SlackMessage(notification));
        when(configuration.getNotifyUsersURI()).thenReturn(uri);
        when(configuration.getAuthToken()).thenReturn(authToken);
        when(requestCreator.createJsonPostRequest(authToken, notification)).thenReturn(slackRequest);
        when(sender.post(URI.create(uri), slackRequest)).thenReturn(slackResponse);

        HttpEntity response = client.notifyUsers(notification);

        Assertions.assertEquals(slackResponse, response);
    }

    @Test
    void itGetsAUserProfile() {
        String userId = "W000000";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("user", userId);
        HttpEntity<MultiValueMap<String, String>> slackRequest = new HttpEntity<>(body);

        String displayName = "John Doe";
        UserProfile profile = new UserProfile();
        profile.setReal_name(displayName);
        UserProfileResponse response = new UserProfileResponse();
        response.setProfile(profile);

        String uri = "https://slack.com/api/users.profile.get";
        String authToken = "xoxp-test-auth-token";
        when(configuration.getNotifyUsersURI()).thenReturn(uri);
        when(configuration.getAuthToken()).thenReturn(authToken);
        when(requestCreator.createUrlEncodedPostRequest(authToken, body)).thenReturn(slackRequest);
        when(sender.postForUser(URI.create(uri), slackRequest)).thenReturn(new HttpEntity<>(response));

        UserProfile userProfile = client.getUserProfile(userId);

        Assertions.assertEquals(displayName, userProfile.getReal_name());
    }
}