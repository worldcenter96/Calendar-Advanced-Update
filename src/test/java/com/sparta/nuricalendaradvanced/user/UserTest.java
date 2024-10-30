package com.sparta.nuricalendaradvanced.user;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserTest {

//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    @Rollback(value = false)
//    void insert() {
//
//        User user = new User();
//        user.setEmail("emailtest@example.com");
//        user.setUsername("홍길동");
//        user.setPassword("1234");
//        userRepository.save(user);
//
//    }
//
//    @Test
//    @Rollback(value = false)
//    void update() {
//
//        User user = userRepository.findById("emailtest@example.com").orElseThrow(NullPointerException::new);
//        user.setPassword("5678");
//        userRepository.save(user);
//
//    }
//
//    @Test
//    @Rollback(value = false)
//    void delete() {
//
//        User user = userRepository.findById("emailtest@example.com").orElseThrow(NullPointerException::new);
//        userRepository.delete(user);
//
//    }


}
