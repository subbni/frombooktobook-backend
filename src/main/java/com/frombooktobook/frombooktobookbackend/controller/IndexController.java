package com.frombooktobook.frombooktobookbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "This is FromBookToBook, welcome to join us.";
    }
}
