package com.licenta.repository;

import com.licenta.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findAllByOrderByNameAsc();

    @Query("SELECT c FROM Profesor c WHERE c.deleted=false")
    List<Profesor> findAllNotDeleted();

    Profesor findByName(String name);
}
