package com.sparta.nuricalendaradvanced.domain.comment.controller;

import com.sparta.nuricalendaradvanced.domain.comment.dto.CommentRequestDto;
import com.sparta.nuricalendaradvanced.domain.comment.dto.CommentResponseDto;
import com.sparta.nuricalendaradvanced.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}")
    public CommentResponseDto submitComment(@PathVariable Long scheduleId, @RequestBody @Valid CommentRequestDto requestDto) {
        return commentService.submitComment(scheduleId, requestDto);
    }

    @GetMapping("/{scheduleId}")
    public List<CommentResponseDto> findAllComment(@PathVariable Long scheduleId) {
        return commentService.findAllComment(scheduleId);
    }

    @PutMapping("/{commentId}")
    public CommentResponseDto modifyComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto requestDto) {
        return commentService.modifyComment(commentId, requestDto);
    }

    @DeleteMapping("/{commentId}")
    public Long deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }


}
