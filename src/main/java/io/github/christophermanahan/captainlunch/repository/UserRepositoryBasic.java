package io.github.christophermanahan.captainlunch.repository;

import io.github.christophermanahan.captainlunch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepositoryBasic extends JpaRepository<User, Long> {
}
