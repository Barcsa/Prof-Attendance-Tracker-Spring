package com.licenta.repository;

import com.licenta.model.Curs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursRepository extends JpaRepository<Curs, Long> {

    @Query("SELECT c FROM Curs c WHERE c.profesor.id = :profesorId and c.profesor.deleted=false and c.deleted=false")
    List<Curs> findByProfesor(long profesorId);

    @Query("SELECT c FROM Curs c WHERE c.deleted=false and c.profesor.deleted=false")
    List<Curs> findAllNotDeleted();

}
