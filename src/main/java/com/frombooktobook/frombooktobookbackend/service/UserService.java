package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.EmailVertifyRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.user.PasswordChangeRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.comment.CommentRepository;
import com.frombooktobook.frombooktobookbackend.domain.liked.LikedRepository;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.exception.EmailVerifyCodeNotMatchException;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import com.frombooktobook.frombooktobookbackend.exception.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LikedRepository likedRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void deleteUser(User user){
        userRepository.deleteById(user.getId());
        likedRepository.deleteByUser(user);
        commentRepository.deleteByWriter(user);
    }

    public User findByEmail(String email) {
        User member = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
        return member;
    }

    public User getCurrentUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User","id",id));
    }

    @Transactional
    public User updateUser(User user, String name, String imgUrl) {
        user.update(name,imgUrl);
        return userRepository.save(user);
    }

    @Transactional
    public String changePasswordToTempPassword(User user) {
        String tempPassword;
        tempPassword = createRandomCode(8);
        user.setPassword(passwordEncoder.encode(tempPassword));
        return tempPassword;
    }

    @Transactional
    public void changePasswordToNewPassword(PasswordChangeRequestDto requestDto) {
        User user = findByEmail(requestDto.getEmail());
        if(checkIfPasswordIsCorrect(user,requestDto.getCurrentPassword())) {
            user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        } else {
            throw new WrongPasswordException("password wrong.");
        }
    }


    public boolean checkIfPasswordIsCorrect(User user, String password) {
        String realPassword = user.getPassword();
        System.out.println(realPassword+"::"+passwordEncoder.encode(password));
        return passwordEncoder.matches(password,realPassword);
    }

    @Transactional
    public String setMailCode(String email) {
        User user = findByEmail(email);
        String code = createRandomCode(6);
        user.setMailCode(code);
        return code;
    }


    @Transactional
    public void  vertifyMailCode(EmailVertifyRequestDto requestDto) {
        User user = findByEmail(requestDto.getEmail());
        if(requestDto.getCode().equals(user.getMailCode())) {
            setMailVertified(user, true);
        } else {
            throw new EmailVerifyCodeNotMatchException("이메일 인증 코드가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void setMailVertified(User user, boolean vertified) {
        user.setMailVertified(vertified);
    }

    public boolean checkIfVertified(String email) {
        User user = findByEmail(email);
        if(user.getMailVertified()) {
            return true;
        } else {
            return false;
        }
    }

    public String createRandomCode(int length) {
        return UUID.randomUUID().toString().substring(0,length);
    }

}
