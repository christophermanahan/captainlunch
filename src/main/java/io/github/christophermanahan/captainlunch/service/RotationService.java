package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.model.User;

public interface RotationService {

    public void rotate();
    public void rotateIntoHead(String userIdentity);
    public User getHeadOfRotation();
    public User getNextInRotation();
}
