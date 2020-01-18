package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Unloading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnloadingRepository extends JpaRepository<Unloading, Long>{
    boolean existsById(Long id);
    boolean existsByAddressIgnoreCase(@Param("Address") String address);
}
