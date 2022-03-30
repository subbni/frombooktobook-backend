package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.controller.post.PostListResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.post.PostResponseDto;
import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post savePost(Post post) {
         postRepository.save(post);
         return post;
    }
//
//    public PostResponseDto findById(Long id) {
//        Post post = postRepository.findById(id).orElseThrow(()->
//                new IllegalArgumentException("해당 게시글이 없습니다. id=" +id));
//        return new PostResponseDto(post);
//    }
//
//    public List<PostListResponseDto> findAllByDesc() {
//        return postRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).stream()
//                .map(PostListResponseDto::new)
//                .collect(Collectors.toList());
//    }

}
