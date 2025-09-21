package com.example.event.booking.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // A1, B2, etc.

    private String zone; // VIP, PREMIUM, NORMAL, EMPTY

    private boolean blocked;
}
