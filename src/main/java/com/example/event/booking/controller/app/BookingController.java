package com.example.event.booking.controller.app;

import com.example.event.booking.dao.BookingRepository;
import com.example.event.booking.dao.EventRepository;
import com.example.event.booking.model.Booking;
import com.example.event.booking.model.Event;
import com.example.event.booking.payload.BookingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;

    public BookingController(BookingRepository bookingRepository,
                             EventRepository eventRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping("/confirmBooking")
    public ResponseEntity<Booking> confirmBooking(@RequestBody BookingRequest request) {
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));


        Booking booking = Booking.builder()
                .event(event)
                .seats(request.getSeats())
                .totalPrice(request.getTotalPrice())
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .bookedAt(LocalDateTime.now())
                .build();

        Booking saved = bookingRepository.save(booking);

        return ResponseEntity.ok(saved); // ✅ returns JSON
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        return bookingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

