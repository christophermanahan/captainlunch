package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.web.slack.UserProfile;
import org.springframework.http.HttpEntity;

public interface Client {
    HttpEntity notifyUsers(String body);
    UserProfile getUserProfile(String userId);
}
