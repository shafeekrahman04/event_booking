package com.example.event.booking.service;

import com.example.event.booking.dao.BookingRepository;
import com.example.event.booking.model.Booking;
import com.example.event.booking.model.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking confirmBooking(Event event, List<String> seats,
                                  int totalPrice, String name, String email) {
        Booking booking = Booking.builder()
                .event(event)
                .seats(seats)
                .totalPrice(totalPrice)
                .customerName(name)
                .customerEmail(email)
                .bookedAt(LocalDateTime.now())
                .build();

        return bookingRepository.save(booking);
    }
}

