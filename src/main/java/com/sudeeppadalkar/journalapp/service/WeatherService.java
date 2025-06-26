package com.sudeeppadalkar.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sudeeppadalkar.journalapp.response.WeatherResponse;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${weather.api.endpoint}")
    private String API;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String uri = API.replace("CITY", city).replace("API_KEY", weatherApiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                WeatherResponse.class);
        return response.getBody();
    }

}
