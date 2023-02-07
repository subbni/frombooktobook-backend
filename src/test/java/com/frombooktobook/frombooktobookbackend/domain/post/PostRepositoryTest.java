package com.frombooktobook.frombooktobookbackend.domain.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    public void post_저장() {
        //given
//        String bookName = "테스트 bookName";
//        String bookAuthor = "테스트 bookAuthor";
//        String content = "테스트 content";
//        int rate = 5;
//
//        postRepository.save(Post.builder()
//                .bookName(bookName)
//                .bookAuthor(bookAuthor)
//                .title(bookName+"을 읽고")
//                .content(content)
//                .rate(rate)
//                .build());
//
//        //when
//        List<Post> postList = postRepository.findAll();
//
//        //then
//        Post post = postList.get(0);
//
//        assertThat(post.getBookName()).isEqualTo(bookName);
//        assertThat(post.getBookAuthor()).isEqualTo(bookAuthor);
//        assertThat(post.getRate()).isEqualTo(rate);

    }
}
