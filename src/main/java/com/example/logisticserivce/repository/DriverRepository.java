package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    boolean existsById(Long id);
    boolean existsByLoginIgnoreCase(@Param("Login") String login);
}
