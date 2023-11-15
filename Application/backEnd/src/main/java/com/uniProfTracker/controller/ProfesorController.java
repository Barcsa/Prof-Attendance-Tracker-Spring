package com.licenta.controller;


import com.licenta.controller.requestbody.PasswordRequest;
import com.licenta.controller.requestbody.ProfesorRequest;
import com.licenta.controller.responses.IdDTO;
import com.licenta.controller.responses.ProfesorResp;
import com.licenta.model.Profesor;
import com.licenta.model.Role;
import com.licenta.model.User;
import com.licenta.repository.ProfesorRepository;
import com.licenta.service.ProfesorService;
import com.licenta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ProfesorController {


    @Autowired
    private UserService userService;

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorRepository profesorRepository;

    /**
     * GET ALL PROFESORI
     */
    @GetMapping(path = "/profesori")
    public List<ProfesorResp> getAllProfesori() {
        List<Profesor> profesori = profesorService.getAllProfesors();


        return profesori.stream().filter(Objects::nonNull).filter(profesor -> !profesor.getName().equals("N/A")).map(profesor -> ProfesorResp.fromEntity(profesor)).collect(Collectors.toList());
    }

    /**
     * GET PROFESOR BY ID
     */
    @RequestMapping(path = "/profesori/{id}", method = RequestMethod.GET)
    public ProfesorResp profesorById(@PathVariable(value = "id") long id) {
        return ProfesorResp.fromEntity(profesorService.getProfesorById(id).get());
    }

    @RequestMapping(path = "/profesori/security/{id}", method = RequestMethod.GET)
    public List<ProfesorResp> profesorByIdforSecurity(@PathVariable(value = "id") long id) {
        return List.of(ProfesorResp.fromEntity(profesorService.getProfesorById(id).get()));
    }

    /**
     * ADD NEW PROFESOR
     */


    @PostMapping("/api/profesori")
    public IdDTO addProfesor(@Validated @RequestBody ProfesorRequest addProfesorRequest, HttpServletRequest httpServletRequest) throws IOException {
        Optional<User> userForMail = userService.getByEmail(addProfesorRequest.getMail());
        User user;

        if (userForMail.isPresent()) {
            user = userForMail.get();
            Profesor profesor = user.getProfesor();
            profesor.setDeleted(false);
            profesorRepository.save(profesor);

            userService.setUserPassword(user, addProfesorRequest.getPassword());
            profesor.setDeleted(false);
            user.setEmail(addProfesorRequest.getMail());
            user.setName(addProfesorRequest.getName());
            user.setProfesor(profesor);
            user.setCreatedDate(new Date());
            userService.save(user);

            return IdDTO.fromId(profesor.getId());
        } else {
            Profesor profesor = profesorService.addProfesor(addProfesorRequest);

            // Create user for profesor
            user = new User();
            user.setEmail(profesor.getMail());
            user.setName(profesor.getName());
            user.setProfesor(profesor);
            user.setCreatedDate(new Date());
            userService.setUserRoles(user, Arrays.asList(Role.PROFESOR));
            userService.setUserPassword(user, addProfesorRequest.getPassword());
            userService.save(user);

            Map<String, Object> mailParameters = new HashMap<>();

            mailParameters.put("name", user.getSurname() + " " + user.getName());
            mailParameters.put("pass", addProfesorRequest.getPassword());
            mailParameters.put("email", user.getEmail());

            return IdDTO.fromId(profesor.getId());
        }
    }

    /**
     * EDIT PROFESOR BY ID
     */
    @RequestMapping(path = "/api/profesori/{id}", method = RequestMethod.PUT)
    public ResponseEntity editProfesor(@PathVariable(value = "id") long id, @Validated @RequestBody ProfesorRequest editProfesorRequest) {
        try {
            return ResponseEntity.ok(IdDTO.fromId(profesorService.editProfesor(id, editProfesorRequest)));
        } catch (IllegalArgumentException | IOException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    // Should be DELETE request, but react admin does not accept it
    @RequestMapping(path = "/profesori/{id}/delete", method = RequestMethod.GET)
    public void deleteCurs(@PathVariable(value = "id") long id) {
        profesorService.softDeleteProfesor(id);
    }
    /**
     * EDIT PROFESOR BY ID
     */

    @RequestMapping(path = "/api/profesori/changePassword", method = RequestMethod.POST)
    public ResponseEntity updatePassword(@Validated @RequestBody PasswordRequest passwordRequest) {
        try {
            return ResponseEntity.ok(IdDTO.fromId(profesorService.updatePassword(passwordRequest)));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
