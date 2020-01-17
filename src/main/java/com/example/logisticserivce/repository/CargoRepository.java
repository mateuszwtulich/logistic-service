package com.example.logisticserivce.repository;

import com.example.logisticserivce.model.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    @Query("select c from Cargo c where c.id = :Id and c.type = :Type")
    Long findCargo(@Param("Type") String Type, @Param("Id") Long Id);

    @Query("select c from Cargo c where c.type = :Type")
    Cargo findCargo(@Param("Type") String type);

    boolean existsById(Long id);

    boolean existsByTypeIgnoreCase(@Param("Type") String type);
    boolean existsByTypeIgnoreCaseAndId(@Param("Type") String type, @Param("Id") Long id);
}
