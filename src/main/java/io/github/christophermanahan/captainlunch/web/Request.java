package io.github.christophermanahan.captainlunch.web;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

public interface Request {
    HttpEntity createJsonPostRequest(String authToken, String body);
    HttpEntity<MultiValueMap<String, String>> createUrlEncodedPostRequest(String authToken, MultiValueMap<String, String> body);
}
