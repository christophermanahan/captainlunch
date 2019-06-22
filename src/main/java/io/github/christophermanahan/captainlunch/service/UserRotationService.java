package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;

public interface UserRotationService {

    public User getHeadOfRotation();
    public User getNextInRotation();
    public void rotate();
}
