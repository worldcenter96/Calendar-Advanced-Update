package com.sparta.nuricalendaradvanced.service;

import com.sparta.nuricalendaradvanced.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.dto.ScheduleResponseDto;
import com.sparta.nuricalendaradvanced.entity.Post;
import com.sparta.nuricalendaradvanced.entity.Schedule;
import com.sparta.nuricalendaradvanced.entity.User;
import com.sparta.nuricalendaradvanced.repository.PostRepository;
import com.sparta.nuricalendaradvanced.repository.ScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PostRepository postRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PostRepository postRepository) {
        this.scheduleRepository = scheduleRepository;
        this.postRepository = postRepository;

    }


    public ScheduleResponseDto submitSchedule(ScheduleRequestDto requestDto, HttpServletRequest req) {

        User user = (User) req.getAttribute("user");
        Schedule schedule = new Schedule(requestDto);

        Post post = new Post();
        post.setUser(user);
        post.setSchedule(schedule);

        Schedule submit = scheduleRepository.save(schedule);
        postRepository.save(post);

        return new ScheduleResponseDto(submit);

    }

    public List<ScheduleResponseDto> findAllSchedule(int page) {

        Page<Post> postPage = postRepository.findAllPost(PageRequest.of(page - 1, 10));
        return postPage.stream().map(ScheduleResponseDto::new).toList();

    }

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

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
