package com.sparta.nuricalendaradvanced.common.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j(topic = "WeatherApi")
@Service
public class WeatherApiService {

    private final RestTemplate restTemplate;

    public WeatherApiService(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public List<WeatherDto> searchWeather() throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://f-api.github.io")
                .path("/f-api/weather.json")
                .encode()
                .build()
                .toUri();
        log.info("uri: " + uri);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        log.info("statusCode: " + responseEntity.getStatusCode());
        log.info("Body: " + responseEntity.getBody());

        return fromJSONtoWeather(responseEntity.getBody());


    }


    public List<WeatherDto> fromJSONtoWeather(String responseEntity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(responseEntity, new TypeReference<List<WeatherDto>>() {
        });


    }

    public Optional<String> getWeatherByDate(List<WeatherDto> weatherDtoList, String date) {
        return weatherDtoList.stream()
                .filter(weatherDto -> weatherDto.getDate().equals(date))
                .map(WeatherDto::getWeather)
                .findFirst();
    }


}
