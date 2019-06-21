package io.github.christophermanahan.captainlunch.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends UserRepositoryBasic, UserRepositoryCustom {
}
