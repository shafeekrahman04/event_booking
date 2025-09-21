package com.example.event.booking.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class OrganizerController {
    private static final String ADMIN_PATH = "admin/";

    @GetMapping("/event-organizer")
    public String getEventOrganizerPage() {
        return ADMIN_PATH + "organizer-events";
    }

    @GetMapping("/organizer")
    public String getOrganizerPage() {
        return ADMIN_PATH + "organizer";
    }


}
