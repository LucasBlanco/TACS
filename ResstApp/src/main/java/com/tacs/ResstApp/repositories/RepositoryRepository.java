package com.tacs.ResstApp.repositories;

import com.tacs.ResstApp.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
}
