package com.frombooktobook.frombooktobookbackend.controller;

import com.frombooktobook.frombooktobookbackend.controller.post.PostListResponseDto;
import com.frombooktobook.frombooktobookbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class IndexController {

    private final PostService postService;
    private final HttpSession httpSession;

}
