package com.sparta.nuricalendaradvanced.domain.comment.service;

import com.sparta.nuricalendaradvanced.common.exception.ResponseException;
import com.sparta.nuricalendaradvanced.common.exception.ResponseStatus;
import com.sparta.nuricalendaradvanced.domain.comment.dto.CommentRequestDto;
import com.sparta.nuricalendaradvanced.domain.comment.dto.CommentResponseDto;
import com.sparta.nuricalendaradvanced.domain.comment.entity.Comment;
import com.sparta.nuricalendaradvanced.domain.comment.repository.CommentRepository;
import com.sparta.nuricalendaradvanced.domain.schedule.entity.Schedule;
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


    public CommentResponseDto submitComment(Long scheduleId, CommentRequestDto requestDto) throws ResponseException {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new ResponseException(ResponseStatus.SCHEDULE_NOT_FOUND));
        Comment comment = commentRepository.save(Comment.from(requestDto, schedule));

        return CommentResponseDto.of(comment);
    }

    public List<CommentResponseDto> findAllComment(Long scheduleId) {

        return commentRepository.findAllByScheduleId(scheduleId).stream()
                .map(CommentResponseDto::of).toList();

    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto) throws ResponseException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResponseException(ResponseStatus.COMMENT_NOT_FOUND));

        return CommentResponseDto.of(comment.update(requestDto));
    }


    public void deleteComment(Long commentId) {

        commentRepository.deleteById(commentId);
    }


}
