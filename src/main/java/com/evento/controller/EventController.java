package com.evento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evento.exception.InvalidEntityException;
import com.evento.model.Event;
import com.evento.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {


    @Autowired
    private EventService eventService;


    @PostMapping("/add")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        Event savedEvent = eventService.createEvent(event);
        return ResponseEntity.ok(savedEvent);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getEvents(){
        List<Event> events=eventService.getAllEvents();

        return ResponseEntity.ok(events);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) throws InvalidEntityException {
        Event event = eventService.getEventById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> getEventsByUser(@PathVariable Long userId) {
    List<Event> events = eventService.getEventsByUser(userId);
    return ResponseEntity.ok(events);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) throws InvalidEntityException {
        Event event = eventService.getEventById(id);
        if (event != null) {
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
