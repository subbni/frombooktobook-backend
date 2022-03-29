package com.frombooktobook.frombooktobookbackend.domain.user;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    User findByNickname(String nickname);
}
