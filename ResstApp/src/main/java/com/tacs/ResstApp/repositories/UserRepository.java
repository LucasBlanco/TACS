package com.tacs.ResstApp.repositories;

import com.tacs.ResstApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
