package com.example.event.booking.payload;

import com.example.event.booking.model.Seat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequest {
    private String title;
    private LocalDateTime dateTime;
    private String location;
    private String image;
    private Double rating;
    private Integer priceVIP;
    private Integer pricePremium;
    private Integer priceNormal;
    private Integer rows;
    private Integer cols;
    private List<Seat> seats;
}
