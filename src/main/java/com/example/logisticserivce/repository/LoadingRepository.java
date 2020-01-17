package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Loading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingRepository extends JpaRepository<Loading, Long>
{
}
