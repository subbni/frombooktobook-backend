package com.frombooktobook.frombooktobookbackend.controller;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.controller.user.UserCreateRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.user.UserResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void user_저장() throws Exception {
        //given
        String email = "slfkak@gamil.com";
        String nickname= "lifeisegg";
        String password = "mypassword";

        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
        String url = "http://localhost:"+port+"/user/register";

        // when
        ResponseEntity<UserResponseDto> responseEntity = restTemplate
                .postForEntity(url,requestDto,UserResponseDto.class);

        // userController.createUser(requestDto);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        User user = userRepository.findByEmail(email);
        assertThat(user.getNickname()).isEqualTo(nickname);

    }
}
