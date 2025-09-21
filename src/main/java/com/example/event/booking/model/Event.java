package com.example.event.booking.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime dateTime;

    private String location;

    private String image;

    private Double rating;

    private Integer priceVIP;

    private Integer pricePremium;

    private Integer priceNormal;

    private String status; // PENDING / APPROVED / REJECTED

    private Long organizerId;

    private LocalDateTime submittedAt;

    private String rejectionReason;
    @Column(name = "`rows`")
    private Integer rows;

    @Column(name = "`cols`")
    private Integer cols;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private List<Seat> seats;

}
