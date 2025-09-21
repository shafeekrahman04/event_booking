package com.example.event.booking.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;


    @ElementCollection
    private List<String> seats;

    private int totalPrice;

    private String customerName;
    private String customerEmail;
    private LocalDateTime bookedAt;
}
