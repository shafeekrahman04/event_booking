package com.example.event.booking.dao;

import com.example.event.booking.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    List<AppUser> findAllByRoleNotAndDeleted(String role, String deleted);
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByUsernameAndPassword(String username, String password);
    Optional<AppUser> findByEmail(String email);


}
