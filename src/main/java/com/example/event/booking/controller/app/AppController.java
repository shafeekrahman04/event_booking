package com.example.event.booking.controller.app;

import com.example.event.booking.dao.BookingRepository;
import com.example.event.booking.dao.EventRepository;
import com.example.event.booking.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/v1")
public class AppController {
    private static final String PUBLIC_PATH = "public/";

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/home")
    public String getHomepage(Model model) {
        // Fetch events from DB (assume you have EventRepository injected)
        List<Event> events = eventRepository.findByStatus("PUBLISHED");

        // Sort events by date descending for hero section
        List<Event> recentEvents = events.stream()
                .sorted((a, b) -> b.getDateTime().compareTo(a.getDateTime()))
                .limit(2)
                .toList();

        // Pick any 3 events for featured section
        List<Event> featuredEvents = events.stream()
                .limit(3)
                .toList();

        model.addAttribute("recentEvents", recentEvents);
        model.addAttribute("featuredEvents", featuredEvents);

        return PUBLIC_PATH + "index";
    }


    @GetMapping("/browse")
    public String getBrowsePage(Model model) {
        List<Event> approvedEvents = eventRepository.findByStatus("PUBLISHED");

        model.addAttribute("events", approvedEvents);

        return PUBLIC_PATH + "browse";
    }

    @GetMapping("/booking-form/{id}")
    public String showBookingForm(@PathVariable Long id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Get booked seats for this event
        List<String> bookedSeats = bookingRepository.findByEventId(id)
                .stream()
                .flatMap(b -> b.getSeats().stream())
                .toList();

        model.addAttribute("event", event);
        model.addAttribute("bookedSeats", bookedSeats);
        return PUBLIC_PATH +  "booking-form";
    }


    @PostMapping("/checkout")
    public String postCheckout(
            @RequestParam Long eventId,
            @RequestParam String seats,
            @RequestParam int totalPrice,
            Model model) {

        // Convert seats JSON string to List
        ObjectMapper mapper = new ObjectMapper();
        List<String> seatList = new ArrayList<>();
        try {
            seatList = mapper.readValue(seats, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Fetch event from DB
        Event event = eventRepository.findById(eventId).get();

        // Pass to model for Thymeleaf checkout page
        model.addAttribute("event", event);
        model.addAttribute("seats", seatList);
        model.addAttribute("totalPrice", totalPrice);

        return PUBLIC_PATH + "checkout"; // Thymeleaf template
    }


    @GetMapping("/book-confirm/{id}")
    public String getConfirmationPage(@PathVariable Long id) {
        return PUBLIC_PATH + "confirmation";
    }
}
