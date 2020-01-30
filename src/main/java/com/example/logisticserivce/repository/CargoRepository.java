package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Cargo;
import com.example.logisticserivce.model.entity.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    boolean existsById(Long id);
    boolean existsByTypeIgnoreCaseAndPrincipal(@Param("Type") String type, @Param("Principal") Principal principal);
}
