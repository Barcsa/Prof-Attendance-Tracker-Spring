package com.licenta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.controller.requestbody.CursFilter;
import com.licenta.controller.requestbody.CursRequest;
import com.licenta.controller.requestbody.EditCursRequest;
import com.licenta.controller.responses.CursResp;
import com.licenta.controller.responses.IdDTO;
import com.licenta.model.Curs;
import com.licenta.repository.CursRepository;
import com.licenta.service.CursService;
import com.licenta.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class CursController {

    @Autowired
    private CursService cursService;

    @Autowired
    private CursRepository cursRepository;

    @Autowired
    private ProfesorService profesorService;


    /**
     * GET ALL Curs
     */
    @GetMapping(path = "/cursuri")
    public List<CursResp> getAllCursuri() {
        return cursService.getAllCursuri().stream().map(CursResp::fromEntity).collect(Collectors.toList());
    }

    /**
     * GET Curs BY ID
     */
    @RequestMapping(path = "/cursuri/{id}", method = RequestMethod.GET)
    public CursResp getCursById(@PathVariable(value = "id") long id) {
        return CursResp.fromEntity(cursService.getCursById(id).orElseThrow(() -> new IllegalArgumentException("curs not found")));
    }

    // Should be DELETE request, but react admin does not accept it
    @RequestMapping(path = "/cursuri/{id}/delete", method = RequestMethod.GET)
    public void deleteCurs(@PathVariable(value = "id") long id) {
        cursService.softDeleteCurs(id);
    }


    /**
     * ADD Curs
     *
     * @return dummy id dto for backoffice - used at nothing - framework requirement
     */
    @PostMapping(path = "/api/cursuri")
    public ResponseEntity addCurs(@Validated @RequestBody CursRequest addCursRequest) {
        cursService.addCurs(addCursRequest);
        return ResponseEntity.ok(IdDTO.fromId(0));
    }

    /**
     * EDIT Curs
     */

    @RequestMapping(path = "/api/cursuri/{id}", method = RequestMethod.PUT)
    public IdDTO editCurs(@PathVariable(value = "id") long id,
                          @Validated @RequestBody EditCursRequest editCursRequest) throws IOException {
        return IdDTO.fromId(cursService.editCurs(id, editCursRequest));
    }


    @GetMapping("/api/cursuri/profesor/{id}")
    public List<CursResp> getAllCursuriForProfesor(@PathVariable("id") String id) {
        List<Curs> cursList = profesorService.getCursListForProfesor(Long.parseLong(id));
        return cursList.stream().map(CursResp::fromEntity).collect(Collectors.toList());
    }

    /**
     * GET ALL CursList
     */
    @GetMapping(path = "/api/cursuri")
    public List<CursResp> getAllCursuri(@RequestParam(name = "filter", required = false) String provFilter) throws IOException {
        CursFilter filter = new ObjectMapper().readValue(provFilter, CursFilter.class);
        List<Curs> result;
        if (filter.getProfesor() == null) {
            result = cursRepository.findAllNotDeleted();
        } else {
            result = cursRepository.findByProfesor(filter.getProfesor());
        }
        return result.stream().filter(Objects::nonNull)
                .map(CursResp::fromEntity)
                .collect(Collectors.toList());
    }
}
