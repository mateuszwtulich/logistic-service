package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Loading;
import com.example.logisticserivce.model.entity.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingRepository extends JpaRepository<Loading, Long> {
    boolean existsById(Long id);
    boolean existsByAddressIgnoreCaseAndPrincipal(@Param("Address") String address, @Param("Principal") Principal principal);
}
