package com.tacs.ResstApp.repositories;

import com.tacs.ResstApp.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {

    Optional<Repository> findByName(String name);

}
