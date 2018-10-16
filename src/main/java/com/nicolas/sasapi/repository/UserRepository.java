package com.nicolas.sasapi.repository;

import com.nicolas.sasapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
