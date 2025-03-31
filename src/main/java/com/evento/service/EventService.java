package com.evento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evento.exception.InvalidEntityException;
import com.evento.model.Event;
import com.evento.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public Event createEvent(Event event){
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents(){

        return eventRepository.findAll();
    }

    public Event getEventById(Long id) throws InvalidEntityException{
       Optional<Event> event=eventRepository.findById(id);
       if (event.isPresent()) {
          return event.get();
       }
       throw new InvalidEntityException("Event does not exists for Id:"+id);

    }
}
