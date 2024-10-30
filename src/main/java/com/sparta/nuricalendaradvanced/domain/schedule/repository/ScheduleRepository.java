package com.sparta.nuricalendaradvanced.domain.schedule.repository;

import com.sparta.nuricalendaradvanced.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findScheduleById(Long id) {
        return findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
