package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User findByEmail(String email) {
        User member = userRepository.findByEmail(email).orElse(null);
        return member;
    }

    public User getCurrentUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User","id",id));
    }

    @Transactional
    public User updateMember(User member, String name, String imgUrl) {
        member.update(name,imgUrl);
        return userRepository.save(member);
    }





}
