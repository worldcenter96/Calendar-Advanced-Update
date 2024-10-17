package com.sparta.nuricalendaradvanced.repository;

import com.sparta.nuricalendaradvanced.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p INNER JOIN Schedule s ON p.schedule.id = s.id INNER JOIN User u ON p.user.id = u.id ORDER BY s.updatedAt DESC")
    Page<Post> findAllPost(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.schedule.id=?1")
    Post findByScheduleId(Long id);
}
