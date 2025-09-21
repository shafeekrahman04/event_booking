package com.example.event.booking.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long eventId;
    private List<String> seats;
    private int totalPrice;
    private String customerName;
    private String customerEmail;
}