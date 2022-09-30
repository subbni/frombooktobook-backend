package com.frombooktobook.frombooktobookbackend.domain.comment;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findByPost(Post post, Pageable pageable);
    List<Comment> findByPost(Post post);
    void deleteByPost(Post post);
    void deleteByWriter(User writer);

}
