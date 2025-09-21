package com.example.event.booking.controller.admin;

import com.example.event.booking.dao.AppUserRepository;
import com.example.event.booking.model.AppUser;
import com.example.event.booking.model.Event;
import com.example.event.booking.payload.EventRequest;
import com.example.event.booking.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/organizer")
public class OrganizerController {

    @Autowired
    private EventService eventService;

    @Autowired
    private AppUserRepository appUserRepository;

    private static final String ADMIN_PATH = "admin/";

    @GetMapping
    public String getEventOrganizerPage(Principal principal, Model model) {
        String username = principal.getName();
        AppUser loggedInUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        List<Event> events = eventService.getEventByOrganizerId(loggedInUser.getId());
        model.addAttribute("events", events);

        return ADMIN_PATH + "organizer-events";
    }


    @GetMapping("/add")
    public String getOrganizerPage() {
        return ADMIN_PATH + "organizer-add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> saveEvent(@RequestBody EventRequest request, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        try {
            AppUser user = appUserRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Event event = Event.builder()
                    .title(request.getTitle())
                    .dateTime(request.getDateTime())
                    .location(request.getLocation())
                    .image(request.getImage())
                    .rating(request.getRating())
                    .priceVIP(request.getPriceVIP())
                    .pricePremium(request.getPricePremium())
                    .priceNormal(request.getPriceNormal())
                    .status("PENDING") // you can also add a boolean field like 'approved'
                    .rows(request.getRows())
                    .cols(request.getCols())
                    .organizerId(user.getId())
                    .submittedAt(LocalDateTime.now())
                    .seats(request.getSeats())
                    .build();

            eventService.saveEvent(event);

            response.put("success", true);
            response.put("message", "Event saved successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }

        return response;
    }

    @GetMapping("/edit/{id}")
    public String getOrganizerEditPage(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("events", event);
        return ADMIN_PATH + "organizer-edit";
    }

    @PostMapping("/edit/{id}")
    @ResponseBody
    public Map<String, Object> updateEvent(@PathVariable Long id,
                                           @RequestBody EventRequest request,
                                           Principal principal) {
        Map<String, Object> response = new HashMap<>();
        try {
            AppUser user = appUserRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Event event = eventService.getEventById(id);

            if (!event.getOrganizerId().equals(user.getId())) {
                throw new RuntimeException("You are not authorized to edit this event");
            }

            // Update fields
            event.setTitle(request.getTitle());
            event.setDateTime(request.getDateTime());
            event.setLocation(request.getLocation());
            event.setImage(request.getImage());
            event.setRating(request.getRating());
            event.setPriceVIP(request.getPriceVIP());
            event.setPricePremium(request.getPricePremium());
            event.setPriceNormal(request.getPriceNormal());
            event.setSeats(request.getSeats());
            event.setRows(request.getRows());
            event.setCols(request.getCols());

            eventService.saveEvent(event);

            response.put("success", true);
            response.put("message", "Event updated successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }

        return response;
    }
}
