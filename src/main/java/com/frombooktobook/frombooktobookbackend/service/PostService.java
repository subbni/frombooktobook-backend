package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostListResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostUpdateRequestDto;
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
    public Post savePost(Post post) {
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
        post.update(requestDto.getBookName(), requestDto.getBookAuthor(), requestDto.getTitle(),
                requestDto.getContent(), requestDto.getRate());
    }

    // post id로 post 조회
    public PostResponseDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" +postId));

        updatePostViews(postId);
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto findPostByIdAndSetIsWriter(Long postId, Long userId ) {
        Post post = updatePostViews(postId);
        PostResponseDto responseDto = new PostResponseDto(post);
        responseDto.setIsWriter(checkIsWriter(userId,postId));
        return responseDto;
    }

    public boolean checkIsWriter(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        //    System.out.println(post.getWriter().getId() +" : "+userId);
        return post.getWriter().getId()==userId? true:false;
    }

    // 전체 조회 (내림차순)
    public List<PostListResponseDto> findAllPostByDesc() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }


    // 전체 조회 페이지네이션
    public Page<PostListResponseDto> getAllPostPage(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return new PageImpl<PostListResponseDto>(postToDto(page.getContent()),
                pageable, page.getTotalElements());
    }

    // 사용자 작성 포스트 조회
    public List<PostListResponseDto> findPostListByUser(User user) {
        return postRepository.findByWriter(user).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 사용자 작성 포스트 조회 페이지네이션
    public Page<PostListResponseDto> getPostListPageByUser(User user, Pageable pageable) {
        Page<Post> page = postRepository.findByWriter(user,pageable);
        return new PageImpl<PostListResponseDto>(postToDto(page.getContent()),
                pageable, page.getTotalElements());
    }

    public  List<PostListResponseDto> postToDto(List<Post> postList) {
        return postList.stream().map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Post updatePostViews(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        post.updateView(1);
        return post;
    }
}
