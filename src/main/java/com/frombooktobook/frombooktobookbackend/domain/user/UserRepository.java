package com.frombooktobook.frombooktobookbackend.domain.user;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
