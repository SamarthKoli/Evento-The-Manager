package com.evento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evento.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    List<Event> findByUserId(Long userId);
}
