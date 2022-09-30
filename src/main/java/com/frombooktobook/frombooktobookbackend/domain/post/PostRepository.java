package com.frombooktobook.frombooktobookbackend.domain.post;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByWriter(User user);
    Page<Post> findByWriter(User user,Pageable pageable);
}
