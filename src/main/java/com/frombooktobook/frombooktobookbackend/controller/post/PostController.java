package com.frombooktobook.frombooktobookbackend.controller.post;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.ApiResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostCreateRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostListResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostUpdateRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.security.CurrentUser;
import com.frombooktobook.frombooktobookbackend.security.JwtUserDetails;
import com.frombooktobook.frombooktobookbackend.service.PostService;
import com.frombooktobook.frombooktobookbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/write")
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostCreateRequestDto requestDto) {

        User user = userService.findByEmail(requestDto.getUserEmail());

        try {
            Post post = postService.savePost(requestDto.toEntity(user));
            if (post == null) {
                System.out.println("post가 null입니다.");
            }
            return ResponseEntity.ok()
                    .body(new PostResponseDto(post));

        } catch (Exception e) {
            System.out.println(e);
        }

        return ResponseEntity.ok()
                .body(new PostResponseDto(requestDto.toEntity(user)));
    }

    // 내림차순 : 제일 최신에 작성된 순서대로 (id가 큰 순서대로)
    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> showPostList() {
        return ResponseEntity.ok()
                .body(postService.findAllPostByDesc());
    }

    @GetMapping("/paging")
    public Page<PostListResponseDto> showPostListPage(Pageable pageable) {
        return postService.getAllPostPage(pageable);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> showPost(@PathVariable("id") Long postId, @CurrentUser JwtUserDetails userDetails) {
        PostResponseDto responseDto = postService.findPostByIdAndSetIsWriter(postId,userDetails.getId());

        return ResponseEntity.ok()
                .body(responseDto);
    }


    @GetMapping("/my")
    public Page<PostListResponseDto> showUserPostListPage(@RequestParam("email") String email,Pageable pageable )  {
        User user = userService.findByEmail(email);
       return  postService.getPostListPageByUser(user,pageable);
    }


    // ** DeleteMapping으로 하면 계속 오류가 나는데 이유가 뭔지 모르겠다.
    @GetMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable("id") Long postId) {
        try {
            postService.deletePost(postId);
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(new ApiResponseDto(false, e.getMessage()));
        }

        return ResponseEntity.ok()
                .body(new ApiResponseDto(true, "delete success"));

    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponseDto> updatePost(@RequestBody PostUpdateRequestDto requestDto) {
        try {
            postService.updatePost(requestDto);
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(new ApiResponseDto(false, e.getMessage()));
        }
        return ResponseEntity.ok()
                .body(new ApiResponseDto(true, "update success"));
    }


}