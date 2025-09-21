package com.example.event.booking.config;

import com.example.event.booking.dao.AppUserRepository;
import com.example.event.booking.model.AppUser;
import com.example.event.booking.reftype.YNStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(AppUserRepository appUserRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            String defaultUsername = "admin";

            if (appUserRepository.findByUsername(defaultUsername).isEmpty()) {
                AppUser admin = AppUser.builder()
                        .username(defaultUsername)
                        .name("System Administrator")
                        .mobileNumber("9999999999")
                        .email("admin@busbooking.com")
                        .password(passwordEncoder.encode("123"))
                        .role("ADMIN")
                        .deleted(YNStatus.NO.getStatus())
                        .createdBy("SYSTEM")
                        .createdOn(LocalDateTime.now())
                        .build();

                appUserRepository.save(admin);
                System.out.println("✅ Default Admin User inserted with encoded password");
            } else {
                System.out.println("ℹ️ Admin User already exists, skipping insert");
            }
        };
    }
}
