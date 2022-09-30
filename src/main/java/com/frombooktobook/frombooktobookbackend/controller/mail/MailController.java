package com.frombooktobook.frombooktobookbackend.controller.mail;

import com.frombooktobook.frombooktobookbackend.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mail")
@RequiredArgsConstructor
@RestController
public class MailController {

    private final MailService mailService;

//    @GetMapping("/{email}")
//    public String test(@PathVariable String email) {
//        try{
//            mailService.sendMail(email);
//        } catch(Exception e ) {
//            return "failed.";
//        }
//
//        return "email sent";
//    }


}
