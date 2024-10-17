package com.sparta.nuricalendaradvanced.controller;

import com.sparta.nuricalendaradvanced.dto.CommentRequestDto;
import com.sparta.nuricalendaradvanced.dto.CommentResponseDto;
import com.sparta.nuricalendaradvanced.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/submit/{scheduleId}")
    public CommentResponseDto submitComment(@PathVariable Long scheduleId, @RequestBody @Valid CommentRequestDto requestDto) {
        return commentService.submitComment(scheduleId, requestDto);
    }

    @GetMapping("/list/{scheduleId}")
    public List<CommentResponseDto> findAllComment(@PathVariable Long scheduleId) {
        return commentService.findAllComment(scheduleId);
    }

    @PutMapping("/modify/{commentId}")
    public CommentResponseDto modifyComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto requestDto) {
        return commentService.modifyComment(commentId, requestDto);
    }

    @DeleteMapping("/delete/{commentId}")
    public Long deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }


}
