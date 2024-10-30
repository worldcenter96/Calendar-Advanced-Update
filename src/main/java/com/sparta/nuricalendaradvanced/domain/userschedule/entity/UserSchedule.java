package com.sparta.nuricalendaradvanced.domain.userschedule.entity;

import com.sparta.nuricalendaradvanced.domain.schedule.entity.Schedule;
import com.sparta.nuricalendaradvanced.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;


}
