package com.hacktm.event;

import com.hacktm.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public List<Event> getEvents(Pageable pageable) {
        List<Event> events = eventRepository.findAll(pageable).getContent();
        return events;
    }

    @GetMapping("/events/{id}")
    public Event getById(@PathVariable long id) {
        Optional<Event> maybeEvent = eventRepository.findById(id);
        if(!maybeEvent.isPresent()) {
            throw new NotFoundException("Event with id " + id + " not found!");
        }
        return maybeEvent.get();
    }

    @DeleteMapping
    public void deleteById(@PathVariable long id) {
        Optional<Event> maybeEvent = eventRepository.findById(id);
        if(!maybeEvent.isPresent()) {
            throw new NotFoundException("Event with id " + id + " not found!");
        }
        eventRepository.deleteById(id);
    }
}
