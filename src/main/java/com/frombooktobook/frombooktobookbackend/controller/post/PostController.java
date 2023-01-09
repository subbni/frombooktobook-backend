package com.frombooktobook.frombooktobookbackend.controller.post;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.ApiResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostCreateRequestDto;
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


@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ApiResponseDto createPost(
            @RequestBody PostCreateRequestDto requestDto) {
        try{
            postService.savePost(requestDto);
            return new ApiResponseDto(true,"작성 완료되었습니다.");
        } catch(Exception e) {
            return new ApiResponseDto(false,"작성에 실패하였습니다. "+e.getMessage());
        }
    }

    @GetMapping("/show/{id}")
    public PostResponseDto showPost(@PathVariable("id") Long postId, @CurrentUser JwtUserDetails userDetails) {
        Post post = postService.findPostById(postId);
        postService.updatePostHit(post);
        return new PostResponseDto(post,postService.checkIsWriter(post,userDetails.getId()));
    }

    @GetMapping("/show/page")
    public Page<PostResponseDto> showPostListPage(Pageable pageable) {
        return postService.getAllPostPage(pageable);
    }


    @GetMapping("/my")
    public Page<PostResponseDto> showUserPostListPage(@RequestParam("email") String email,Pageable pageable )  {
       return  postService.getPostListPageByUser(email,pageable);
    }


    // ** DeleteMapping으로 하면 계속 오류가 나는데 이유가 뭔지 모르겠다.
    @GetMapping("/delete/{id}")
    public ApiResponseDto deletePost(@PathVariable("id") Long postId) {
        try {
            postService.deletePost(postId);
            return new ApiResponseDto(true, "삭제 완료되었습니다.");
        } catch (Exception e) {
            return new ApiResponseDto(false, "삭제에 실패하였습니다. "+e.getMessage());
        }
    }

    @PostMapping("/update")
    public ApiResponseDto updatePost(@RequestBody PostUpdateRequestDto requestDto) {
        try {
            postService.updatePost(requestDto);
            return new ApiResponseDto(true, "수정 완료되었습니다.");
        } catch (Exception e) {
            return new ApiResponseDto(false, "수정에 실패하였습니다. "+e.getMessage());

        }

    }


}