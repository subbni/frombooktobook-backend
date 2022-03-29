package com.frombooktobook.frombooktobookbackend.controller.user;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequestDto requestDto) {
       User user =  userService.searchUser(requestDto.toEntity().getEmail());

       // 해당 email의 user가 존재하는 경우
       if(user != null) {
           return ResponseEntity.ok()
                   .body(new UserResponseDto(user));
       }


       return ResponseEntity.ok()
               .body(new UserResponseDto(userService.saveUser(requestDto.toEntity())));
    }

    @GetMapping("/{email}")
    public User findByUser(@PathVariable String email) {
        return userService.searchUser(email);
    }
}
