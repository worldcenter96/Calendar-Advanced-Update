package com.sparta.nuricalendaradvanced.service;

import com.sparta.nuricalendaradvanced.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.dto.ScheduleResponseDto;
import com.sparta.nuricalendaradvanced.dto.ScheduleResponsePage;
import com.sparta.nuricalendaradvanced.entity.Post;
import com.sparta.nuricalendaradvanced.entity.Schedule;
import com.sparta.nuricalendaradvanced.entity.User;
import com.sparta.nuricalendaradvanced.repository.PostRepository;
import com.sparta.nuricalendaradvanced.repository.ScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PostRepository postRepository;


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

        Post post = postRepository.findByScheduleId(id);
        return new ScheduleResponseDto(post);

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
        Post post = postRepository.findByScheduleId(id);
        schedule.getCommentList()
                .forEach(comment -> {
                    System.out.println(comment.getComment());
                });

        // 자식 테이블 데이터 삭제 후 부모 테이블 데이터 삭제
        postRepository.delete(post);
        scheduleRepository.delete(schedule);

        return id;
    }

}
