package com.licenta.service;

import com.licenta.model.Role;
import com.licenta.model.User;
import com.licenta.model.UserRole;
import com.licenta.repository.UserRepository;
import com.licenta.security.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        Optional<User> userById = userRepository.findById(id);
        return userById;
    }

    public Optional<User> getByEmail(String email) {
        Optional<User> userById = userRepository.findByEmail(email);
        return userById;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void setUserRoles(User user, List<Role> roles) {
        user.setRoles(roles.stream().map(role -> new UserRole(user, role)).collect(Collectors.toList()));
    }

    public void setUserPassword(User user, String password) {
        user.setPassword(new BCryptPasswordEncoder().encode(password));
    }
}
