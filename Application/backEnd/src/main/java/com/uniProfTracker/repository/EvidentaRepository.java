package com.licenta.repository;

import com.licenta.model.Evidenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EvidentaRepository extends JpaRepository<Evidenta, Long> {

    @Query("SELECT c FROM Evidenta c WHERE c.profesor.id = :profesorId and c.profesor.deleted=false and c.deleted=false")
    List<Evidenta> findByProfesor(long profesorId);

    @Query("SELECT c FROM Evidenta c WHERE c.deleted=false and c.profesor.deleted=false")
    List<Evidenta> findAllNotDeleted();
}
