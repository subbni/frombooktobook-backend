package com.frombooktobook.frombooktobookbackend.controller.comment;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.ApiResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.comment.dto.CommentCreateRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.comment.dto.CommentResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.comment.dto.CommentUpdateRequestDto;
import com.frombooktobook.frombooktobookbackend.security.CurrentUser;
import com.frombooktobook.frombooktobookbackend.security.JwtUserDetails;
import com.frombooktobook.frombooktobookbackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/comment")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write")
    public CommentResponseDto createComment(
            @RequestBody CommentCreateRequestDto requestDto
            ) {
            CommentResponseDto responseDto = commentService.saveComment(requestDto);
            return responseDto;
    }

    @PostMapping("/update")
    public CommentResponseDto updateComment(
            @RequestBody CommentUpdateRequestDto requestDto
            ) {
        CommentResponseDto responseDto = commentService.updateComment(requestDto);
        return responseDto;
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable("id") Long commentId) {
        try{
            commentService.deleteComment(commentId);
        }catch(Exception e) {
            return ResponseEntity.ok()
                    .body(new ApiResponseDto(false, e.getMessage()));
        }
        return ResponseEntity.ok()
                .body(new ApiResponseDto(true, "delete success"));
    }


    @GetMapping
    public Page<CommentResponseDto> showCommentListPage(@CurrentUser JwtUserDetails userDetails, @RequestParam("id") Long postId, Pageable pageable) {
        return commentService.getCommentListPage(pageable,postId,userDetails.getId());
    }


}
