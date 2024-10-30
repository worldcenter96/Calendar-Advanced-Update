package com.sparta.nuricalendaradvanced.domain.comment.controller;

import com.sparta.nuricalendaradvanced.common.exception.ResponseException;
import com.sparta.nuricalendaradvanced.domain.comment.dto.CommentRequestDto;
import com.sparta.nuricalendaradvanced.domain.comment.dto.CommentResponseDto;
import com.sparta.nuricalendaradvanced.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<CommentResponseDto> submitComment(@RequestParam Long scheduleId,
                                                            @RequestBody @Valid CommentRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.submitComment(scheduleId, requestDto));
    }

    @GetMapping()
    public ResponseEntity<List<CommentResponseDto>> findAllComment(@RequestParam Long scheduleId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.findAllComment(scheduleId));
    }

    @PutMapping()
    public ResponseEntity<CommentResponseDto> updateComment(@RequestParam Long commentId,
                                                            @RequestBody @Valid CommentRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(commentId, requestDto));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteComment(@RequestParam Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }


}
