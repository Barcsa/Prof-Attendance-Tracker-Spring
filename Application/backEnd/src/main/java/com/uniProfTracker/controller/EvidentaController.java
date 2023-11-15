package com.licenta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.controller.requestbody.CursFilter;
import com.licenta.controller.requestbody.EvidentaRequest;
import com.licenta.controller.responses.EvidentaResponse;
import com.licenta.controller.responses.IdDTO;
import com.licenta.model.Evidenta;
import com.licenta.repository.EvidentaRepository;
import com.licenta.service.EvidentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class EvidentaController {

    @Autowired
    private EvidentaService evidentaService;

    @Autowired
    private EvidentaRepository evidentaRepository;

    /**
     * GET ALL Evidenta
     */
//    @GetMapping(path = "/evidente")
//    public List<EvidentaResponse> getAllEvidente(@RequestParam(name = "filter", required = false) String provFilter) throws IOException {
//        CursFilter filter = new ObjectMapper().readValue(provFilter, CursFilter.class);
//        List<Evidenta> result;
//        if (filter.getProfesor() == null) {
//            result = evidentaService.getAllEvidenta();
//        } else {
//            result = evidentaRepository.findByProfesor(filter.getProfesor());
//        }
//        return result.stream().map(EvidentaResponse::fromEntity).collect(Collectors.toList());
//    }

    /**
     * GET Evidenta BY ID
     */
    @RequestMapping(path = "/evidente/{id}", method = RequestMethod.GET)
    public EvidentaResponse getEvidentaById(@PathVariable(value = "id") long id) {
        return EvidentaResponse.fromEntity(evidentaService.getEvidentaById(id).orElseThrow(() -> new IllegalArgumentException("evidenta not found")));
    }

    // Should be DELETE request, but react admin does not accept it
    @RequestMapping(path = "/evidente/{id}/delete", method = RequestMethod.GET)
    public void deleteEvidenta(@PathVariable(value = "id") long id) {
        evidentaService.softDeleteEvidenta(id);
    }


    /**
     * ADD Evidenta
     *
     * @return dummy id dto for backoffice - used at nothing - framework requirement
     */
    @PostMapping(path = "/api/evidente")
    public ResponseEntity addEvidenta(@Validated @RequestBody EvidentaRequest evidentaRequest) throws IOException {
        evidentaService.addEvidenta(evidentaRequest);
        return ResponseEntity.ok(IdDTO.fromId(0));
    }

    /**
     * EDIT Evidenta
     */

    @RequestMapping(path = "/api/evidente/{id}", method = RequestMethod.PUT)
    public IdDTO editEvidenta(@PathVariable(value = "id") long id,
                              @Validated @RequestBody EvidentaRequest evidentaRequest) throws IOException {
        return IdDTO.fromId(evidentaService.editEvidenta(id, evidentaRequest));
    }


    @GetMapping("/api/evidente/profesor/{id}")
    public List<EvidentaResponse> getAllEvidenteForProfesor(@PathVariable("id") String id) {
        List<Evidenta> evidentaList = evidentaService.getEvidentaListForProfesor(Long.parseLong(id));
        return evidentaList.stream().map(EvidentaResponse::fromEntity).collect(Collectors.toList());
    }

    /**
     * GET ALL CursList
     */
    @GetMapping(path = "/api/evidente")
    public List<EvidentaResponse> getAllEvidenteByFilter(@RequestParam(name = "filter", required = false) String provFilter) throws IOException {
        CursFilter filter = new ObjectMapper().readValue(provFilter, CursFilter.class);
        List<Evidenta> result = new ArrayList<>();
        if (filter.getProfesor() == null) {
            result = evidentaService.getAllEvidenta();
        } else {
            result = evidentaService.getEvidentaListForProfesor(filter.getProfesor());
        }
        return result.stream().filter(Objects::nonNull)
                .map(EvidentaResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
