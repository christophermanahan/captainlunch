package io.github.christophermanahan.captainlunch.service;

import org.springframework.http.HttpEntity;

public interface ValidationService {

    public Boolean validateRequest(HttpEntity<String> request);
}
