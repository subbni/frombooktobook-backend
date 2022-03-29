package com.frombooktobook.frombooktobookbackend.domain;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void user저장_불러오기() {
        //given
        String email = "slfkak@naver.com";
        String nickname = "subbni";
        String password = "password123";


        userRepository.save(User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPassword()).isEqualTo(password);


    }

    @Test
    public void user_email로_찾기() {
        //given
        String email = "slfkak@naver.com";
        String nickname = "subbni";
        String password = "password123";


        userRepository.save(User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build());

        //when
        User user = userRepository.findByEmail(email);

        //then
        assertThat(user.getPassword()).isEqualTo(email);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPassword()).isEqualTo(password);
    }
}
