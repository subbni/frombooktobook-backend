package com.frombooktobook.frombooktobookbackend.domain;

import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    UserRepository memberRepository;

    @AfterEach
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Test
    public void user저장_불러오기() {
    }


}
