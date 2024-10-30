package com.sparta.nuricalendaradvanced.domain.schedule.service;

import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleResponseDto;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleResponsePage;
import com.sparta.nuricalendaradvanced.domain.userschedule.entity.UserSchedule;
import com.sparta.nuricalendaradvanced.domain.schedule.entity.Schedule;
import com.sparta.nuricalendaradvanced.domain.userschedule.repository.UserScheduleRepository;
import com.sparta.nuricalendaradvanced.domain.schedule.repository.ScheduleRepository;
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
    private final UserScheduleRepository userScheduleRepository;


    // 일정 생성
    public ScheduleResponseDto submitSchedule(ScheduleRequestDto requestDto) {

        // DTO -> Entity
        Schedule schedule = Schedule.from(requestDto);

        // DB 저장
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Entity -> DTO
        return savedSchedule.to();

    }

    // 일정 전체 조회(페이징 적용)
    public ScheduleResponsePage findAllSchedule(int page, int size, String criteria) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);
        return new ScheduleResponsePage(schedules);


    }

    // 일정 전체 조회
//    public List<ScheduleResponseDto> findAll() {
//
//        List<Schedule> schedules = scheduleRepository.findAll();
//        return schedules.stream()
//                .map(Schedule::to)
//                .toList();
//
//    }

    public ScheduleResponseDto findByIdSchedule(Long id) {

        UserSchedule userSchedule = userScheduleRepository.findByScheduleId(id);
        return new ScheduleResponseDto(userSchedule);

    }

    @Transactional
    public ScheduleResponseDto modifySchedule(Long id, ScheduleRequestDto requestDto) {

        Schedule schedule = findById(id);
        schedule.update(requestDto);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public Long deleteSchedule(Long id) {

        Schedule schedule = findById(id);
        UserSchedule userSchedule = userScheduleRepository.findByScheduleId(id);
        schedule.getCommentList()
                .forEach(comment -> {
                    System.out.println(comment.getComment());
                });

        // 자식 테이블 데이터 삭제 후 부모 테이블 데이터 삭제
        userScheduleRepository.delete(userSchedule);
        scheduleRepository.delete(schedule);

        return id;
    }

}
