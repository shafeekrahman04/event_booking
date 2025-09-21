package com.example.event.booking.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class AdminController {
    private static final String ADMIN_PATH = "admin/";

    @GetMapping("/event-admin")
    public String getEventAdminPage() {
        return ADMIN_PATH + "events-admin";
    }

}
