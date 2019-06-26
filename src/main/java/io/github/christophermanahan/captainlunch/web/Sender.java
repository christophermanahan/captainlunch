package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.web.slack.UserProfileResponse;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import java.net.URI;

public interface Sender {
    HttpEntity post(URI location, HttpEntity request);
    HttpEntity<UserProfileResponse> postForUser(URI location, HttpEntity<MultiValueMap<String, String>> request);
}
