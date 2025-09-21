package com.example.event.booking.service;


import com.example.event.booking.model.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendTicketEmail(Booking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(booking.getCustomerEmail());
            helper.setSubject("🎟 Your Ticket Confirmation - " + booking.getEvent().getTitle());

            String seatList = String.join(", ", booking.getSeats());
            String date = booking.getEvent().getDateTime()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            String body = """
                    <h2>✅ Booking Confirmed</h2>
                    <p>Hi %s,</p>
                    <p>Thank you for booking with <b>EventX</b>!</p>
                    
                    <h3>🎶 Event Details:</h3>
                    <ul>
                        <li><b>Event:</b> %s</li>
                        <li><b>Date:</b> %s</li>
                        <li><b>Venue:</b> %s</li>
                        <li><b>Seats:</b> %s</li>
                        <li><b>Total Paid:</b> ₹%d</li>
                    </ul>
                    
                    <p>Show this email at the venue as your ticket. 🎟</p>
                    <br/>
                    <p>Regards,<br/>EventX Team</p>
                    """.formatted(
                    booking.getCustomerName(),
                    booking.getEvent().getTitle(),
                    date,
                    booking.getEvent().getLocation(),
                    seatList,
                    booking.getTotalPrice()
            );

            helper.setText(body, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
