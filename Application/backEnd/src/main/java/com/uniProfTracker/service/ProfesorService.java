package com.licenta.service;


import com.licenta.controller.requestbody.PasswordRequest;
import com.licenta.controller.requestbody.ProfesorRequest;
import com.licenta.model.Curs;
import com.licenta.model.Profesor;
import com.licenta.model.User;
import com.licenta.repository.CursRepository;
import com.licenta.repository.ProfesorRepository;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private CursRepository cursRepository;

    @Autowired
    private UserService userService;

    public List<Profesor> getAllProfesors() {
        return profesorRepository.findAllNotDeleted();
    }

    public Optional<Profesor> getProfesorById(long id) {
        Optional<Profesor> ProfesorById = profesorRepository.findById(id);
        return ProfesorById;
    }

    /**
     * add Profesor
     *
     * @return
     */
    @Transactional
    public Profesor addProfesor(ProfesorRequest addProfesorRequest) throws IOException {
        Profesor newProfesor = new Profesor();
        newProfesor.setName(addProfesorRequest.getName());
        newProfesor.setMail(addProfesorRequest.getMail());
        newProfesor.setPhone(addProfesorRequest.getPhone());

        return profesorRepository.save(newProfesor);
    }

    /**
     * EDIT Profesor
     */
    @Transactional
    public Long editProfesor(long id, ProfesorRequest ProfesorRequest) throws IOException {
        Profesor editProfesor = profesorRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Profesor with id" + id + "not found"));

        editProfesor.setName(ProfesorRequest.getName());
        editProfesor.setPhone(ProfesorRequest.getPhone());

        return profesorRepository.save(editProfesor).getId();
    }


    @Transactional
    public List<Curs> getCursListForProfesor(long id) {
        Profesor Profesor = profesorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Profesor found for id " + id));
        Hibernate.initialize(Profesor.getCursuri());
        List<Curs> cursuri= cursRepository.findByProfesor(id);
        return cursuri;
    }

    @Transactional
    public void softDeleteProfesor(long id) {
        profesorRepository.findById(id).ifPresent(profesor -> {
            profesor.setDeleted(true);
            profesorRepository.save(profesor);
        });
    }

    public boolean verifyPassword(String password, String ecodedPassword) {


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean passwordsMatch = passwordEncoder.matches(password, ecodedPassword);

        if (passwordsMatch) {
            return true;
        } else {
            return false;
        }
    }
    public long updatePassword(PasswordRequest passwordRequest){

        User user= userService.getByEmail(passwordRequest.getEmail()).get();
        if(verifyPassword(passwordRequest.getPass1()  ,user.getPassword())){
            userService.setUserPassword(user,passwordRequest.getPass2());
            userService.save(user);
            return user.getId();
        }else{
            throw new IllegalArgumentException("Wrong password");
        }
    }

}
