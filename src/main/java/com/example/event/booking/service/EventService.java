package com.example.event.booking.service;

import com.example.event.booking.dao.EventRepository;
import com.example.event.booking.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
    public List<Event> getEventByOrganizerId(Long id) {
        return eventRepository.findByOrganizerId(id);
    }
}
