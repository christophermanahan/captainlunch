package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;

public interface RotationLookupService {

    public User getHeadOfRotation();
    public User getNextInRotation();
}
