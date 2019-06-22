package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;

public interface RotationService {
    public User getHeadOfRotation();
    public User getNextInRotation();
}
