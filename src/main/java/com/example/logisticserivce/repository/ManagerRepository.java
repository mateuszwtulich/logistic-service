package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    boolean existsById(Long id);
    boolean existsByLoginIgnoreCase(@Param("Login") String login);
}
