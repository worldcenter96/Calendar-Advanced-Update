package com.sparta.nuricalendaradvanced.domain.schedule.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.nuricalendaradvanced.common.api.WeatherApiService;
import com.sparta.nuricalendaradvanced.common.exception.ResponseException;
import com.sparta.nuricalendaradvanced.common.exception.ResponseStatus;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleResponseDto;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleResponsePage;
import com.sparta.nuricalendaradvanced.domain.schedule.entity.Schedule;
import com.sparta.nuricalendaradvanced.domain.schedule.repository.ScheduleRepository;
import com.sparta.nuricalendaradvanced.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final WeatherApiService weatherApiService;


    public ScheduleResponseDto submitSchedule(ScheduleRequestDto requestDto, User user) throws JsonProcessingException {

        Schedule schedule = Schedule.from(requestDto, user, findWeatherData());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.of(savedSchedule);

    }

    public ScheduleResponsePage findAllSchedule(int page, int size, String criteria) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);
        return new ScheduleResponsePage(schedules);


    }

    public ScheduleResponseDto findByIdSchedule(Long id) throws ResponseException {

        Schedule schedule = findById(id);
        return ScheduleResponseDto.of(schedule);

    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) throws ResponseException, JsonProcessingException {

        Schedule schedule = findById(id);
        return ScheduleResponseDto.of(schedule.update(requestDto, findWeatherData()));
    }


    public void deleteSchedule(Long id) {

        scheduleRepository.deleteById(id);

    }

    public Schedule findById(Long id) throws ResponseException {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new ResponseException(ResponseStatus.SCHEDULE_NOT_FOUND));
    }

    public String findWeatherData() throws JsonProcessingException {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        String formattedDate = today.format(formatter);

        return String.valueOf(weatherApiService.getWeatherByDate(weatherApiService.searchWeather(), formattedDate));
    }

}
