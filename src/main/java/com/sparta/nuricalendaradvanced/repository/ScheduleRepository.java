package com.sparta.nuricalendaradvanced.repository;

import com.sparta.nuricalendaradvanced.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
