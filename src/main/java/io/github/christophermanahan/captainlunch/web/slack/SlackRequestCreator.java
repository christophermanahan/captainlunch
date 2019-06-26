package io.github.christophermanahan.captainlunch.web.slack;

import io.github.christophermanahan.captainlunch.web.RequestCreator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class SlackRequestCreator implements RequestCreator {

    public HttpEntity<SlackMessage> createJsonPostRequest(String authToken, String body) {
        HttpHeaders headers = getHttpHeaders(authToken, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(new SlackMessage(body), headers);
    }

    public HttpEntity<MultiValueMap<String, String>> createUrlEncodedPostRequest(String authToken, MultiValueMap<String, String> body) {
        HttpHeaders headers = getHttpHeaders(authToken, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return new HttpEntity<>(body, headers);
    }
    
    private HttpHeaders getHttpHeaders(String authToken, String mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, mediaType);
        headers.set(HttpHeaders.AUTHORIZATION, authToken);
        return headers;
    }
}
