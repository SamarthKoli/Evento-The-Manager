package com.evento.model;

import java.time.LocalDateTime;

import com.evento.dto.WeatherInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

    @ManyToOne
    private User user;  // Link to User entity

    private String weatherInfo;
    
    public Event() {
    }
    public Event(Long id, String title, String location, LocalDateTime dateTime,User user, String weatherInfo) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.dateTime = dateTime;
        this.user=user;
        this.weatherInfo=weatherInfo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getWeatherInfo() {
        return weatherInfo;
    }
    public void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
    
    

}
