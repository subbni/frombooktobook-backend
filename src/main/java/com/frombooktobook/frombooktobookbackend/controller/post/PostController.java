package com.frombooktobook.frombooktobookbackend.controller.post;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;


    @PostMapping("/post/write")
    public ResponseEntity<PostResponseDto> CreatePost(
            @RequestBody PostCreateRequestDto requestDto) {
        try {
            Post post = postService.savePost(requestDto.toEntity());
            if(post == null) {
                System.out.println("post가 null입니다.");
            }
            return ResponseEntity.ok()
                    .body(new PostResponseDto(post));

        } catch(Exception e) {
            System.out.println(e);
        }

        return ResponseEntity.ok()
                .body(new PostResponseDto(requestDto.toEntity()));
    }

//    // 내림차순 : 제일 최신에 작성된 순서대로 (id가 큰 순서대로)
//    @GetMapping("/post")
//    public ResponseEntity<List<PostListResponseDto>> postList() {
//        return ResponseEntity.ok()
//                .body(postService.findAllByDesc());
//    }


}