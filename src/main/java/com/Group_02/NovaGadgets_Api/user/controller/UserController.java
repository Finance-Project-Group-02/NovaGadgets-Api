package com.Group_02.NovaGadgets_Api.user.controller;

import com.Group_02.NovaGadgets_Api.user.dto.request.UserLoginDto;
import com.Group_02.NovaGadgets_Api.user.dto.response.UserReponseDto;
import com.Group_02.NovaGadgets_Api.user.model.UsersEntity;
import com.Group_02.NovaGadgets_Api.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    //URL: http://localhost:8080/api/v1/users
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/users")
    public ResponseEntity<List<UserReponseDto>> getAllUsers() {
        return new ResponseEntity<List<UserReponseDto>>(userService.getAllUsers(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/v1/users/{id}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/users/{id}")
    public ResponseEntity<UserReponseDto> getUserById(@PathVariable int id) {
        return new ResponseEntity<UserReponseDto>(userService.getUserById(id), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/v1/users
    //Method: POST
    @Transactional
    @PostMapping("/users")
    public ResponseEntity<UsersEntity> createUser(@Valid @RequestBody UsersEntity user) {
        return new ResponseEntity<UsersEntity>(userService.createUser(user), HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/v1/users/{id}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }


    //URL: http://localhost:8080/api/v1/users
    //Method: POST
    @Transactional
    @PostMapping("/users/login")
    public ResponseEntity<UserReponseDto> login(@Valid @RequestBody UserLoginDto user) {
        return new ResponseEntity<UserReponseDto>(userService.login(user), HttpStatus.CREATED);
    }
}
