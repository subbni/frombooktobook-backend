package com.frombooktobook.frombooktobookbackend.controller.post;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.service.PostService;
import com.frombooktobook.frombooktobookbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/post/write")
    public ResponseEntity<PostResponseDto> CreatePost(
            @RequestBody PostCreateRequestDto requestDto) {
        User user = userService.findByEmail(requestDto.getUserEmail());
        try {
            Post post = postService.savePost(requestDto.toEntity(user));
            if(post == null) {
                System.out.println("post가 null입니다.");
            }
            return ResponseEntity.ok()
                    .body(new PostResponseDto(post));

        } catch(Exception e) {
            System.out.println(e);
        }

        return ResponseEntity.ok()
                .body(new PostResponseDto(requestDto.toEntity(user)));
    }

    // 내림차순 : 제일 최신에 작성된 순서대로 (id가 큰 순서대로)
    @GetMapping("/post")
    public ResponseEntity<List<PostListResponseDto>> postList() {
        return ResponseEntity.ok()
                .body(postService.findAllByDesc());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> showPost(@PathVariable("id") Long postId){
        return ResponseEntity.ok()
                .body(postService.findById(postId));
    }
}