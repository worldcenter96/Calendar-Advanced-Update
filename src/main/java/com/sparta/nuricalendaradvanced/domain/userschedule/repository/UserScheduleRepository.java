package com.sparta.nuricalendaradvanced.domain.userschedule.repository;

import com.sparta.nuricalendaradvanced.domain.userschedule.entity.UserSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {

    @Query("SELECT p FROM UserSchedule p INNER JOIN Schedule s ON p.schedule.id = s.id INNER JOIN User u ON p.user.id = u.id ORDER BY s.updatedAt DESC")
    Page<UserSchedule> findAllPost(Pageable pageable);

    @Query("SELECT p FROM UserSchedule p WHERE p.schedule.id=?1")
    UserSchedule findByScheduleId(Long id);
}
