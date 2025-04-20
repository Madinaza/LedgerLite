package com.ledgerlite.service;

import com.ledgerlite.dto.*;
import com.ledgerlite.entity.UserEntity;
import com.ledgerlite.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public void register(UserDto dto) {
        if (repo.findByUsername(dto.username()).isPresent()) {
            throw new IllegalArgumentException("Username exists");
        }
        var e = new UserEntity();
        e.setUsername(dto.username());
        e.setPasswordHash(encoder.encode(dto.password()));
        e.setRoles(dto.roles());
        repo.save(e);
    }

    public Optional<UserEntity> authenticate(String username, String raw) {
        return repo.findByUsername(username)
                .filter(u -> encoder.matches(raw, u.getPasswordHash()));
    }
}