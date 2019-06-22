package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.web.slack.UserProfileResponse;
import org.springframework.http.HttpEntity;

public interface Client {
    HttpEntity notifyUsers(String body);
    HttpEntity<UserProfileResponse> getUserProfile(String userId);
}
