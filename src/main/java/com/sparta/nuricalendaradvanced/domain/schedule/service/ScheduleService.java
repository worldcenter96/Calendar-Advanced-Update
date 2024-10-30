package com.sparta.nuricalendaradvanced.domain.schedule.service;

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

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    public ScheduleResponseDto submitSchedule(ScheduleRequestDto requestDto, User user) {

        Schedule schedule = Schedule.from(requestDto, user);
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
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) throws ResponseException {

        Schedule schedule = findById(id);
        return ScheduleResponseDto.of(schedule.update(requestDto));
    }


    public void deleteSchedule(Long id) {

        scheduleRepository.deleteById(id);

    }

    public Schedule findById(Long id) throws ResponseException {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new ResponseException(ResponseStatus.SCHEDULE_NOT_FOUND));
    }

}
