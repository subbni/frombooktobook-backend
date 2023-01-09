package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.controller.liked.LikedRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostResponseDto;
import com.frombooktobook.frombooktobookbackend.domain.liked.Liked;
import com.frombooktobook.frombooktobookbackend.domain.liked.LikedRepository;
import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.post.PostRepository;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikedService {
    private final LikedRepository likedRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Liked saveLiked(LikedRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User","email", requestDto.getEmail()));
        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(()->new ResourceNotFoundException("Post","id", requestDto.getPostId()));
        post.addLikedCount();
        return likedRepository.save(Liked.builder().user(user).post(post).build());
    }


    @Transactional
    public void deleteLiked(LikedRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User","email", requestDto.getEmail()));
        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(()->new ResourceNotFoundException("Post","id", requestDto.getPostId()));
     try {
         likedRepository.delete(likedRepository.findByUserAndPost(user, post).orElseThrow(
                 ()->new Exception("liked delete failed.")
         ));
         post.subtrackLikedCount();
     } catch(Exception e) {
         System.out.println(e.getMessage());
        }

    }

    public List<PostResponseDto> getLikedPostByUser(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        List<Liked> likedList = likedRepository.findByUser(user);

        return likedList.stream().map(Liked::getPost)
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public boolean IsLikedExist(LikedRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User","email", requestDto.getEmail()));

        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(()->new ResourceNotFoundException("Post","id", requestDto.getPostId()));

        try{
            likedRepository.findByUserAndPost(user,post)
                    .orElseThrow(()->new Exception("liked not found"));
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

}
