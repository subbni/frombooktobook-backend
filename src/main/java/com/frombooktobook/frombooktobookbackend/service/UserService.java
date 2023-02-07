package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.ApiResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.auth.dto.EmailVertifyRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.user.dto.PasswordChangeRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.comment.CommentRepository;
import com.frombooktobook.frombooktobookbackend.domain.liked.LikedRepository;
import com.frombooktobook.frombooktobookbackend.domain.user.Role;
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

    public boolean checkIfExistedEmail(String email) {
        try {
            User user = findByEmail(email);
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
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

//    @Transactional
//    public void changePasswordToNewPassword(PasswordChangeRequestDto requestDto) {
//        User user = findByEmail(requestDto.getEmail());
//        if(checkIfPasswordIsCorrect(user,requestDto.getCurrentPassword())) {
//            user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
//        } else {
//            throw new WrongPasswordException("password wrong.");
//        }
//    }




    public String createRandomCode(int length) {
        return UUID.randomUUID().toString().substring(0,length);
    }

}
