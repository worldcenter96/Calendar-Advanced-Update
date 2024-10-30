package com.sparta.nuricalendaradvanced.domain.comment.service;

import com.sparta.nuricalendaradvanced.domain.comment.dto.CommentRequestDto;
import com.sparta.nuricalendaradvanced.domain.comment.dto.CommentResponseDto;
import com.sparta.nuricalendaradvanced.domain.comment.entity.Comment;
import com.sparta.nuricalendaradvanced.domain.schedule.entity.Schedule;
import com.sparta.nuricalendaradvanced.domain.comment.repository.CommentRepository;
import com.sparta.nuricalendaradvanced.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;


    public CommentResponseDto submitComment(Long scheduleId, CommentRequestDto requestDto) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalArgumentException::new);
        Comment comment = new Comment(requestDto, schedule);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> findAllComment(Long scheduleId) {

        return commentRepository.findAllByScheduleId(scheduleId).stream()
                .map(CommentResponseDto::new).toList();

    }

    @Transactional
    public CommentResponseDto modifyComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(IllegalArgumentException::new);

        Comment updatedComment = comment.update(requestDto);

        return new CommentResponseDto(updatedComment);
    }


    public Long deleteComment(Long commentId) {

        commentRepository.deleteById(commentId);
        return commentId;
    }


}