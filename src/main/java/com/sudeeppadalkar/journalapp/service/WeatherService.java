package com.sudeeppadalkar.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sudeeppadalkar.journalapp.cache.AppCache;
import com.sudeeppadalkar.journalapp.response.WeatherResponse;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String uri = appCache.APP_CACHE.get("weather_api_uri")
                .replace("<city>", city)
                .replace("<api_key>", weatherApiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                WeatherResponse.class);
        return response.getBody();
    }

}
