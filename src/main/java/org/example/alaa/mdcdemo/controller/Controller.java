package org.example.alaa.mdcdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.alaa.mdcdemo.service.Service;
import org.example.alaa.mdcdemo.model.User;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final Service service;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user, HttpServletRequest request) {
        log.info("In Controller: Creation request received for user {}", user);
        service.createUser(user);
        log.info("User {} created successfully", user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUser() throws Exception {
        log.info("Get request received for user {}",MDC.get("X-Correlation-ID"));
        service.getUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
