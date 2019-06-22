package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.web.slack.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class RequestSender implements Sender {

    private RestTemplate restTemplate;

    @Autowired
    RequestSender(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HttpEntity<String> post(URI location, HttpEntity request) {
        return restTemplate.postForEntity(location, request, String.class);
    }

    public HttpEntity<UserProfileResponse> postForUser(URI location, HttpEntity<MultiValueMap<String, String>> request) {
        return restTemplate.postForEntity(location, request, UserProfileResponse.class);
    }
}
