package com.ledgerlite.controller;

import com.ledgerlite.repository.UserRepository;
import com.ledgerlite.entity.UserEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<UserEntity> list() {
        return repo.findAll();
    }
}