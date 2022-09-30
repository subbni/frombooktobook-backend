package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.controller.comment.dto.CommentCreateRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.comment.dto.CommentResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.comment.dto.CommentUpdateRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.comment.Comment;
import com.frombooktobook.frombooktobookbackend.domain.comment.CommentRepository;
import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.post.PostRepository;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    // 1. 댓글 등록
    @Transactional
    public CommentResponseDto saveComment(CommentCreateRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User","email", requestDto.getEmail()));
        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(()->new ResourceNotFoundException("Post","id", requestDto.getPostId()));

        return new CommentResponseDto(commentRepository.save(Comment.builder().user(user).post(post).content(requestDto.getContent()).build()));
    }


    // 2. 댓글 조회 : 페이지네이션
    public Page<CommentResponseDto> getCommentListPage(Pageable pageable,Long postId,Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id", postId));
        Page<Comment> page = commentRepository.findByPost(post,pageable);
        return new PageImpl<CommentResponseDto>(commentToDto(page.getContent(),userId),
                pageable, page.getTotalElements());

    }

    public List<CommentResponseDto> findCommentListByPostId(Post post) {
        return commentRepository.findByPost(post).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<CommentResponseDto> commentToDto(List<Comment> commentList, Long userId) {
        // isWriter 처리
        List<CommentResponseDto> responseDtoList =  commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        responseDtoList.stream().forEach(v->v.setIsWriter(checkIsWriter(userId,v.getId())));
        return responseDtoList;
    }

    // 3. 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(requestDto.getCommentId())
                .orElseThrow(()->new ResourceNotFoundException("Comment","id",requestDto.getCommentId()));
        comment.update(requestDto.getContent());
        return new CommentResponseDto(comment);
    }


    // 4. 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        commentRepository.delete(comment);
    }

    public boolean checkIsWriter(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
        return comment.getWriter().getId()==userId? true:false;
    }


}
