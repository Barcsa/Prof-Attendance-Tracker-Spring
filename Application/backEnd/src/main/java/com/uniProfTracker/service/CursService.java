package com.licenta.service;

import com.licenta.controller.requestbody.CursRequest;
import com.licenta.controller.requestbody.EditCursRequest;
import com.licenta.model.Curs;
import com.licenta.model.Profesor;
import com.licenta.repository.CursRepository;
import com.licenta.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursService {

    private final CursRepository cursRepository;

    private final ProfesorRepository profesorRepository;

    public Optional<Curs> getCursById(long id) {
        Optional<Curs> getCursById = cursRepository.findById(id);
        return getCursById;
    }

    @Transactional
    public void softDeleteCurs(long id) {
        cursRepository.findById(id).ifPresent(curs -> {
            curs.setDeleted(true);
            cursRepository.save(curs);
        });
    }

    public List<Curs> getAllCursuri() {
        List<Curs> allCursuri = cursRepository.findAllNotDeleted();
        return allCursuri;
    }

    /**
     * ADD Curs
     */
    @Transactional
    public void addCurs(CursRequest addCursRequest) {
        Profesor profesor = profesorRepository.findById(addCursRequest.getProfesorId())
                .orElseThrow(() -> new IllegalArgumentException("Profesor not found for id " + addCursRequest.getProfesorId()));


        Curs newCurs = new Curs();
        newCurs.setProfesor(profesor);

        newCurs.setDeleted(false);
        newCurs.setNumeCurs(addCursRequest.getNumeCurs());
        newCurs.setAn(addCursRequest.getAn());
        newCurs.setSpecializare(addCursRequest.getSpecializare());

        cursRepository.save(newCurs);
    }

    /**
     * EDIT Curs
     */
    @Transactional
    public Long editCurs(long id, EditCursRequest editCursRequest) throws IOException {
        Curs editCurs = cursRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Curs with id " + id + "not found"));

        if (editCursRequest.getProfesorId() != 0) {
            editCurs.setProfesor(profesorRepository.findById(editCursRequest.getProfesorId()).orElseThrow(() -> new IllegalArgumentException("Profesor not found for id " + editCursRequest.getProfesorId())));
        }

        editCurs.setDeleted(false);
        editCurs.setNumeCurs(editCursRequest.getNumeCurs());
        editCurs.setSpecializare(editCursRequest.getSpecializare());
        editCurs.setAn(editCursRequest.getAn());

        return cursRepository.save(editCurs).getId();
    }
}
