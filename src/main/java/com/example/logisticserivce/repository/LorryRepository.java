package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Lorry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LorryRepository extends JpaRepository<Lorry, String> {
}
