package com.frombooktobook.frombooktobookbackend.domain.liked;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked,Long> {

    List<Liked> findByUser(User user);
    Optional<Liked> findByUserAndPost(User user, Post post);
    void deleteByPost(Post post);
    void deleteByUser(User user);
}
