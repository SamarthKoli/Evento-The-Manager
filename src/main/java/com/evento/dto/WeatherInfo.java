package com.evento.dto;

import lombok.Data;

@Data
public class WeatherInfo {
    private double temperature;  // Kelvin by default
    private String condition;    // e.g., "Clear"
}