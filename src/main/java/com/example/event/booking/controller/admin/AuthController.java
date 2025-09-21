package com.example.event.booking.controller.admin;

import com.example.event.booking.model.AppUser;
import com.example.event.booking.reftype.YNStatus;
import com.example.event.booking.service.AppUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth/admin")
public class AuthController {

    private final AppUserService service;
    private PasswordEncoder passwordEncoder;

    public AuthController(AppUserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    private static final String ADMIN_PATH = "admin/";

    @GetMapping("/login")
    public String login() {
        return ADMIN_PATH + "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return ADMIN_PATH + "signup";
    }


    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String password,
                         Model model) {
        try {
            AppUser user = AppUser.builder()
                    .username(username)
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role("ORGANIZER")
                    .deleted(YNStatus.NO.getStatus())
                    .build();
            service.signup(user);
            return "redirect:/auth/admin/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/admin/login";
        }
    }


}
