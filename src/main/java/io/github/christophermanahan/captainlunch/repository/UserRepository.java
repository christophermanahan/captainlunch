package io.github.christophermanahan.captainlunch.repository;

import io.github.christophermanahan.captainlunch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByOrderByStartDateDesc();
    User findFirstByOrderByEndDate();
}
