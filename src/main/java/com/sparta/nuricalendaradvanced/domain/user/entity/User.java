package com.sparta.nuricalendaradvanced.domain.user.entity;

import com.sparta.nuricalendaradvanced.domain.common.entity.Timestamped;
import com.sparta.nuricalendaradvanced.domain.userschedule.entity.UserSchedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false, length = 10)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<UserSchedule> userScheduleList = new ArrayList<>();

    public static User from(String username, String email, String password, UserRoleEnum role) {
        User user = new User();
        user.init(username, email, password, role);
        return user;
    }

    private void init(String username, String email, String password, UserRoleEnum role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void update(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
