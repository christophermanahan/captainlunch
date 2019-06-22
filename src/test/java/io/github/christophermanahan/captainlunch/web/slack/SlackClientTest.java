package io.github.christophermanahan.captainlunch.web.slack;

import io.github.christophermanahan.captainlunch.configuration.OutgoingRequestConfiguration;
import io.github.christophermanahan.captainlunch.web.Client;
import io.github.christophermanahan.captainlunch.web.Request;
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
    private Request request;
    private OutgoingRequestConfiguration configuration;
    private Client client;

    @BeforeEach
    void setUp() {
        sender = mock(Sender.class);
        request = mock(Request.class);
        configuration = mock(OutgoingRequestConfiguration.class);
        client = new SlackClient(sender, request, configuration);
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
        when(request.createJsonPostRequest(authToken, notification)).thenReturn(slackRequest);
        when(sender.post(URI.create(uri), slackRequest)).thenReturn(slackResponse);

        HttpEntity response = client.notifyUsers(notification);

        Assertions.assertEquals(slackResponse, response);
    }

    @Test
    void itSendsAProfileRequestToTheConfiguredLocation() {
        String uri = "https://slack.com/api/users.profile.get";
        String authToken = "xoxp-test-auth-token";
        String userId = "W000000";
        MultiValueMap body = new LinkedMultiValueMap<String, String>();
        body.add("user", userId);
        HttpEntity slackRequest = new HttpEntity<>(body);
        HttpEntity<UserProfileResponse> response = new HttpEntity<>(new UserProfileResponse());
        when(configuration.getNotifyUsersURI()).thenReturn(uri);
        when(configuration.getAuthToken()).thenReturn(authToken);
        when(request.createUrlEncodedPostRequest(authToken, body)).thenReturn(slackRequest);
        when(sender.postForUser(URI.create(uri), slackRequest)).thenReturn(response);

        HttpEntity<UserProfileResponse> userProfile = client.getUserProfile(userId);

        Assertions.assertEquals(response, userProfile);
    }
}