package com.frombooktobook.frombooktobookbackend.controller.liked;

import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostResponseDto;
import com.frombooktobook.frombooktobookbackend.service.LikedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/liked")
@RequiredArgsConstructor
@RestController
public class LikedController {

    private final LikedService likedService;
    private final static String LIKED_MESSAGE = "LIKED";
    private final static String UNLIKED_MESSAGE = "UNLIKED";

    //1. 좋아요 버튼 눌렀을 때
    @PostMapping("/push")
    public LikedResponseDto pushLiked(@RequestBody LikedRequestDto requestDto) {
        if(likedService.IsLikedExist(requestDto)) {
            likedService.deleteLiked(requestDto);
            return new LikedResponseDto(false,UNLIKED_MESSAGE);
        }

        likedService.saveLiked(requestDto);
        return new LikedResponseDto(true,LIKED_MESSAGE);
    }

    //2. 좋아요 누른 독후감 리스트 조회
    @GetMapping("/{email}")
    public List<PostResponseDto> likedPostList(@PathVariable String email) {
        return likedService.getLikedPostByUser(email);
    }

    //3. 좋아요 버튼 눌렀는지 확인
    @PostMapping("/pushed-or-not")
    public LikedResponseDto isPushed(@RequestBody LikedRequestDto requestDto) {

        if(likedService.IsLikedExist(requestDto)==true) {
            return new LikedResponseDto(true,LIKED_MESSAGE);
        }

        return new LikedResponseDto(false,UNLIKED_MESSAGE);
    }
}
