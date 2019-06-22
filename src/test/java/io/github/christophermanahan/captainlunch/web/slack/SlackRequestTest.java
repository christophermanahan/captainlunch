package io.github.christophermanahan.captainlunch.web.slack;

import io.github.christophermanahan.captainlunch.web.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

class SlackRequestTest {

    private Request slackRequest;

    @BeforeEach
    void setUp() {
        slackRequest = new SlackRequest();
    }

    @Test
    void createsAnAuthorizedJsonPostRequest() {
        String authToken = "xoxp-test-auth-token";
        String body = "Test request body";

        HttpEntity<SlackMessage> request = slackRequest.createJsonPostRequest(authToken, body);


        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, request.getHeaders().getContentType().toString());
        Assertions.assertEquals(authToken, request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
        Assertions.assertEquals(body, request.getBody().getText());
    }

    @Test
    void createsAnAuthorizedUrlFormEncodedPostRequest() {
        String authToken = "xoxp-test-auth-token";
        String user = "W0000000";
        MultiValueMap body = new LinkedMultiValueMap<String, String>();
        body.add("user", user);

        HttpEntity request = slackRequest.createUrlEncodedPostRequest(authToken, body);


        Assertions.assertEquals(MediaType.APPLICATION_FORM_URLENCODED_VALUE, request.getHeaders().getContentType().toString());
        Assertions.assertEquals(authToken, request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
        Assertions.assertEquals(body, request.getBody());
    }
}