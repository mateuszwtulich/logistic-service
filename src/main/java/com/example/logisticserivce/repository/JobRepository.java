package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsById(Long id);
    boolean existsByNumber(@Param("Number") Long number);
}
