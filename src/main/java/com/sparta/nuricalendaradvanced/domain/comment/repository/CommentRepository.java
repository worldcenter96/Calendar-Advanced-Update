package com.sparta.nuricalendaradvanced.domain.comment.repository;

import com.sparta.nuricalendaradvanced.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.schedule.id = ?1")
    List<Comment> findAllByScheduleId(Long id);
}
