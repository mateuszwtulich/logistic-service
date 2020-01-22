package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Job;
import com.example.logisticserivce.model.entity.JobArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobArchiveRepository extends JpaRepository<JobArchive, Long> {
}
