package com.frombooktobook.frombooktobookbackend.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post,Long> {
    //Optional<Post> findById();
}
