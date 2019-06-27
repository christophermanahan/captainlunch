package io.github.christophermanahan.captainlunch.web.slack;

import io.github.christophermanahan.captainlunch.configuration.OutgoingRequestConfiguration;
import io.github.christophermanahan.captainlunch.web.Client;
import io.github.christophermanahan.captainlunch.web.RequestCreator;
import io.github.christophermanahan.captainlunch.web.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

import static java.util.Objects.requireNonNull;

@Component
public class SlackClient implements Client {

    private Sender sender;
    private OutgoingRequestConfiguration configuration;
    private RequestCreator requestCreator;

    @Autowired
    public SlackClient(Sender sender, RequestCreator requestCreator, OutgoingRequestConfiguration configuration) {
        this.sender = sender;
        this.requestCreator = requestCreator;
        this.configuration = configuration;
    }

    public HttpEntity notifyUsers(String notification) {
        URI location = URI.create(configuration.getNotifyUsersURI());
        HttpEntity slackRequest = requestCreator.createJsonPostRequest(configuration.getAuthToken(), notification);
        return sender.post(location, slackRequest);
    }

    public UserProfile getUserProfile(String userId) {
        URI location = URI.create("https://slack.com/api/users.profile.get");
        HttpEntity<MultiValueMap<String, String>> slackRequest =
                requestCreator.createUrlEncodedPostRequest(configuration.getAuthToken(), getUrlEncodedBody(userId));
        return requireNonNull(sender.postForUser(location, slackRequest).getBody()).getProfile();
    }

    private MultiValueMap<String, String> getUrlEncodedBody(String userId) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("user", userId);
        return body;
    }
}
