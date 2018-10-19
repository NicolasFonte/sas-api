package com.nicolas.sasapi.repository;

import com.nicolas.sasapi.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
