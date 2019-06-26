package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;

public interface CreateUserService {
    User createUser(String identity);
}
