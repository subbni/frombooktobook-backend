package com.frombooktobook.frombooktobookbackend.controller.post;

import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostCreateRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.post.dto.PostResponseDto;
import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.post.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postRepository.deleteAll();
    }

    @Test
    public void post_저장() throws Exception{
        //given
        String bookName="test bookName";
        String bookAuthor="test bookAuthor";
        String content = "test content";
        int rate = 5;

//        PostCreateRequestDto requestDto = PostCreateRequestDto.builder()
//                .bookName(bookName)
//                .bookAuthor(bookAuthor)
//                .title(null)
//                .content(content)
//                .rate(rate)
//                .build();

//        String url = "http://localhost:"+port+"/post/write";
//        // when
//        ResponseEntity<PostResponseDto> responseEntity = restTemplate
//                .postForEntity(url,requestDto,PostResponseDto.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<Post> postList = postRepository.findAll();
//        Post post = postList.get(0);


//        assertThat(post.getBookName()).isEqualTo(bookName);
//        String expectedTitle = bookName+"을 읽고";
//        assertThat(post.getTitle()).isEqualTo(expectedTitle);

    }
}
