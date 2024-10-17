package com.sparta.nuricalendaradvanced.repository;

import com.sparta.nuricalendaradvanced.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.schedule.id = ?1")
    List<Comment> findAllByScheduleId(Long id);
}
