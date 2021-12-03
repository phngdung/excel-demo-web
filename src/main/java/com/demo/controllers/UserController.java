package com.demo.controllers;

import com.demo.entities.User;
import com.demo.exception.CustomException;
import com.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/user/{id}")
    public ResponseEntity getUserById(@PathVariable long id) throws CustomException {
        User user = userRepository.findById(id).orElseThrow( () -> new CustomException("User not found", 404));
        return new ResponseEntity(user, HttpStatus.OK);
    }
}
