package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, Long> {
    boolean existsById(Long id);
    boolean existsByNameIgnoreCase(@Param("Name") String name);
    boolean existsByAddressIgnoreCase(@Param("Address") String address);
}
