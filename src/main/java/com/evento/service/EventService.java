package com.evento.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.evento.dto.WeatherInfo;
import com.evento.exception.InvalidEntityException;
import com.evento.model.Event;
import com.evento.model.User;
import com.evento.repository.EventRepository;
import com.evento.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;



    private final WebClient webClient = WebClient.create("http://api.openweathermap.org/data/2.5/weather");
    @Value("${weather.api.key}")
    private String apiKey; 

    private final ObjectMapper objectMapper = new ObjectMapper();  // For JS

  public Event createEvent(Event event) {
        if (event.getTitle() == null || event.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Event title cannot be empty");
        }
        if (event.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event date cannot be in the past");
        }
        try {
            String weatherJson = getWeather(event.getLocation());
            WeatherInfo weatherInfo = parseWeather(weatherJson);
            event.setWeatherInfo("Temp: " + weatherInfo.getTemperature() + "K, Condition: " + weatherInfo.getCondition());
            // logger.info("Weather for {}: {}", event.getLocation(), weatherInfo);
        } catch (WebClientResponseException.NotFound e) {
            // logger.warn("Weather data not found for {}; proceeding without it.", event.getLocation());
            event.setWeatherInfo("Weather data unavailable");
        } catch (Exception e) {
            // logger.error("Error fetching weather for {}: {}", event.getLocation(), e.getMessage());
            event.setWeatherInfo("Error fetching weather");
        }
        return eventRepository.save(event);
    }

    private WeatherInfo parseWeather(String weatherJson) throws Exception {
        JsonNode root = objectMapper.readTree(weatherJson);
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setTemperature(root.path("main").path("temp").asDouble());
        weatherInfo.setCondition(root.path("weather").get(0).path("main").asText());
        return weatherInfo;
    }

    private String getWeather(String location) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", location)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Simplified; handle async properly in production
    }

    public List<Event> getEventsByUser(Long userId) {
        return eventRepository.findByUserId(userId);
    }

    public List<Event> getAllEvents() {

        return eventRepository.findAll();
    }

    public Event getEventById(Long id) throws InvalidEntityException {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        }
        throw new InvalidEntityException("Event does not exists for Id:" + id);

    }

    public void deleteEvent(Long id) {

        eventRepository.deleteById(id);
    }


}
