package com.licenta.service;

import com.licenta.controller.requestbody.EvidentaRequest;
import com.licenta.model.Curs;
import com.licenta.model.Evidenta;
import com.licenta.model.Profesor;
import com.licenta.repository.CursRepository;
import com.licenta.repository.EvidentaRepository;
import com.licenta.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvidentaService {

    private final EvidentaRepository evidentaRepository;

    private final ProfesorRepository profesorRepository;

    private final CursRepository cursRepository;

    public Optional<Evidenta> getEvidentaById(long id) {
        Optional<Evidenta> getEvidentaById = evidentaRepository.findById(id);
        return getEvidentaById;
    }

    @Transactional
    public void softDeleteEvidenta(long id) {
        evidentaRepository.findById(id).ifPresent(evidenta -> {
            evidenta.setDeleted(true);
            evidentaRepository.save(evidenta);
        });
    }

    public List<Evidenta> getAllEvidenta() {
        List<Evidenta> allEvidenta = evidentaRepository.findAllNotDeleted();
        return allEvidenta;
    }

    /**
     * ADD Evidenta
     */
    @Transactional
    public void addEvidenta(EvidentaRequest evidentaRequest) {
        Curs curs = cursRepository.findById(evidentaRequest.getCursId()).orElseThrow(() -> new IllegalArgumentException("Profesor not found for id " + evidentaRequest.getCursId()));

        Evidenta evidenta = new Evidenta();
        evidenta.setProfesor(curs.getProfesor());
        evidenta.setCurs(curs);
        evidenta.setDeleted(false);
        evidenta.setData(evidentaRequest.getData());
        evidenta.setIntreOre(evidentaRequest.getIntreOre());
        evidenta.setNrOreCurs(evidentaRequest.getNrOreCurs());
        evidenta.setNrOreSLP(evidentaRequest.getNrOreSLP());

        evidentaRepository.save(evidenta);
    }


    /**
     * EDIT Evidenta
     */
    @Transactional
    public Long editEvidenta(long id, EvidentaRequest editEvidentaRequest) throws IOException {
        Evidenta editEvidenta = evidentaRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Evidenta with id " + id + "not found"));

        editEvidenta.setCurs(cursRepository.findById(editEvidentaRequest.getCursId()).orElseThrow(() -> new IllegalArgumentException("Curs not found for id " + editEvidentaRequest.getCursId())));


        editEvidenta.setDeleted(false);
        editEvidenta.setData(editEvidentaRequest.getData());
        editEvidenta.setIntreOre(editEvidentaRequest.getIntreOre());
        editEvidenta.setNrOreCurs(editEvidentaRequest.getNrOreCurs());
        editEvidenta.setNrOreSLP(editEvidentaRequest.getNrOreSLP());

        return evidentaRepository.save(editEvidenta).getId();
    }

    @Transactional
    public List<Evidenta> getEvidentaListForProfesor(long id) {

        return evidentaRepository.findByProfesor(id);
    }
}
