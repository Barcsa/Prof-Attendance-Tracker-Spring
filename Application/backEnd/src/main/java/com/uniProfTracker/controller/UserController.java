package com.licenta.controller;

import com.licenta.OperationResponse;
import com.licenta.controller.requestbody.AddUserRequest;
import com.licenta.controller.requestbody.EditUserRequest;
import com.licenta.controller.responses.UserDTO;
import com.licenta.model.Role;
import com.licenta.model.User;
import com.licenta.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * get all users
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream().filter(Objects::nonNull).map(UserDTO::fromEntity).collect(Collectors.toList());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public UserDTO getUserById(@PathVariable(value = "id") long id) {
        return UserDTO.fromEntity(userService.getUserById(id).orElseThrow(() -> new IllegalArgumentException("user not found")));
    }

    @PostMapping("/create")
    public OperationResponse createUser(@Validated @RequestBody final AddUserRequest addUserRequest, HttpServletRequest httpServletRequest) {
        Optional<User> incomingUser = userService.getByEmail(addUserRequest.getEmail());
        return incomingUser.map(
                (value) -> OperationResponse.of("User with this mail already exists")).orElseGet(() -> {
            User user = createUserAndPersist(addUserRequest);

            userService.save(user);

            return OperationResponse.of("User created successfully", user.getId());
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity editUser(@PathVariable(value = "id") long id, @Validated @RequestBody final EditUserRequest request) {
        User user = userService.getUserById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setMobile(request.getMobile());
        userService.save(user);

        return ResponseEntity.ok().build();
    }

    private User createUserAndPersist(AddUserRequest addUserRequest) {
        User user = new User();
        user.setEmail(addUserRequest.getEmail());
        user.setMobile(addUserRequest.getMobile());
        user.setName(addUserRequest.getName());
        user.setSurname(addUserRequest.getSurname());
        user.setCreatedDate(new Date());

        userService.setUserPassword(user, addUserRequest.getPassword());
        userService.setUserRoles(user, Arrays.asList(Role.PROFESOR));

        return userService.save(user);
    }
}
