package com.example.event.booking.service;

import com.example.event.booking.dao.AppUserRepository;
import com.example.event.booking.model.AppUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class AppUserService {
    private final AppUserRepository repo;

    public AppUserService(AppUserRepository repo) {
        this.repo = repo;
    }

    public Optional<AppUser> login(String username, String password) {
        return repo.findByUsernameAndPassword(username, password);
    }

    public AppUser signup(AppUser user) {
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setCreatedOn(LocalDateTime.now());
        user.setRole(user.getRole() == null ? "organizer" : user.getRole());
        return repo.save(user);
    }
}
