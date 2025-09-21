package com.example.event.booking.controller.admin;

import com.example.event.booking.dao.EventRepository;
import com.example.event.booking.model.Event;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventApiController {

    private final EventRepository eventRepository;

    public EventApiController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // ---------- GET ALL EVENTS ----------
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // ---------- GET SINGLE EVENT ----------
    @GetMapping("/{id}")
    public Event getEvent(@PathVariable Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // ---------- CREATE EVENT ----------
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    // ---------- UPDATE EVENT ----------
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        return eventRepository.findById(id)
                .map(ev -> {
                    ev.setTitle(updatedEvent.getTitle());
                    ev.setDateTime(updatedEvent.getDateTime());
                    ev.setLocation(updatedEvent.getLocation());
                    ev.setImage(updatedEvent.getImage());
                    ev.setRating(updatedEvent.getRating());
                    ev.setPriceVIP(updatedEvent.getPriceVIP());
                    ev.setPricePremium(updatedEvent.getPricePremium());
                    ev.setPriceNormal(updatedEvent.getPriceNormal());
                    ev.setStatus(updatedEvent.getStatus());
                    ev.setRejectionReason(updatedEvent.getRejectionReason());

                    // Optional seat grid
                    ev.setRows(updatedEvent.getRows());
                    ev.setCols(updatedEvent.getCols());

                    // Seats update
                    ev.getSeats().clear();
                    ev.getSeats().addAll(updatedEvent.getSeats());

                    return eventRepository.save(ev);
                })
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // ---------- DELETE EVENT ----------
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
    }

    // ---------- DUPLICATE EVENT ----------
    @PostMapping("/{id}/duplicate")
    public Event duplicateEvent(@PathVariable Long id) {
        Event src = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Event clone = new Event();
        clone.setTitle(src.getTitle() + " (Copy)");
        clone.setDateTime(src.getDateTime());
        clone.setLocation(src.getLocation());
        clone.setImage(src.getImage());
        clone.setRating(src.getRating());
        clone.setPriceVIP(src.getPriceVIP());
        clone.setPricePremium(src.getPricePremium());
        clone.setPriceNormal(src.getPriceNormal());
        clone.setStatus("DRAFT");
        clone.setRows(src.getRows());
        clone.setCols(src.getCols());
        clone.getSeats().addAll(src.getSeats());

        return eventRepository.save(clone);
    }

    // ---------- ACTIONS: APPROVE / REJECT / PUBLISH / UNPUBLISH / ARCHIVE ----------
    @PostMapping("/{id}/approve")
    public Event approveEvent(@PathVariable Long id) {
        return changeStatus(id, "APPROVED", null);
    }

    @PostMapping("/{id}/reject")
    public Event rejectEvent(@PathVariable Long id, @RequestBody(required = false) String reason) {
        return changeStatus(id, "REJECTED", reason);
    }

    @PostMapping("/{id}/publish")
    public Event publishEvent(@PathVariable Long id) {
        return changeStatus(id, "PUBLISHED", null);
    }

    @PostMapping("/{id}/unpublish")
    public Event unpublishEvent(@PathVariable Long id) {
        return changeStatus(id, "APPROVED", null);
    }

    @PostMapping("/{id}/archive")
    public Event archiveEvent(@PathVariable Long id) {
        return changeStatus(id, "ARCHIVED", null);
    }

    // ---------- HELPER ----------
    private Event changeStatus(Long id, String status, String reason) {
        Event ev = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        ev.setStatus(status);
        if ("REJECTED".equals(status)) {
            ev.setRejectionReason(reason != null ? reason : "");
        } else {
            ev.setRejectionReason("");
        }
        return eventRepository.save(ev);
    }
}
