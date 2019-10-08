package com.tacs.ResstApp.repositories;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByFavourites(Repository repository);

}
