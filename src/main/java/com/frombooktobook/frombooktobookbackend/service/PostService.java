package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostCreateRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostUpdateRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.Book;
import com.frombooktobook.frombooktobookbackend.domain.comment.CommentRepository;
import com.frombooktobook.frombooktobookbackend.domain.liked.LikedRepository;
import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.post.PostRepository;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikedRepository likedRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Post savePost(PostCreateRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getUserEmail())
                        .orElseThrow(()->new ResourceNotFoundException("User","email",requestDto.getUserEmail()));
        Post post = requestDto.toEntity(user);
         postRepository.save(post);
         return post;
    }

    @Transactional
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        // cascade.remove를 사용할 수 없음 -> liked의 연관관계가 'user과 post' 모두에 있기 때문.
        likedRepository.deleteByPost(post);
        postRepository.delete(post);
        commentRepository.deleteByPost(post);
    }

    @Transactional
    public void  updatePost(PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(()->new ResourceNotFoundException("Post","id",requestDto.getPostId()));
        Book book = new Book(requestDto.getBookTitle(), requestDto.getBookAuthor(), requestDto.getBookRate());
        post.update(requestDto.getTitle(), requestDto.getContent(), book);
    }


    public Post findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" +postId));
        return post;
    }

    @Transactional
    public Post updatePostHit(Post post) {
        post.addHit();
        return post;
    }


    public boolean checkIsWriter(Post post, Long userId) {
        return post.getWriter().getId()==userId? true:false;
    }


    // 전체 조회 페이지네이션
    public Page<PostResponseDto> getAllPostPage(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return new PageImpl<PostResponseDto>(postToDto(page.getContent()),
                pageable, page.getTotalElements());
    }

    // 사용자 작성 포스트 조회
    public List<PostResponseDto> findPostListByUser(User user) {
        return postRepository.findByWriter(user).stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 사용자 작성 포스트 조회 페이지네이션
    public Page<PostResponseDto> getPostListPageByUser(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new ResourceNotFoundException("User","email",userEmail));
        Page<Post> page = postRepository.findByWriter(user,pageable);
        return new PageImpl<PostResponseDto>(postToDto(page.getContent()),
                pageable, page.getTotalElements());
    }

    public  List<PostResponseDto> postToDto(List<Post> postList) {
        return postList.stream().map(PostResponseDto::new)
                .collect(Collectors.toList());
    }


}
