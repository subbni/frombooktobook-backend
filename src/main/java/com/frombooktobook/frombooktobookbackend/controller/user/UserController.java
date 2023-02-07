package com.frombooktobook.frombooktobookbackend.controller.user;
import com.frombooktobook.frombooktobookbackend.controller.auth.dto.ApiResponseDto;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import com.frombooktobook.frombooktobookbackend.security.CurrentUser;
import com.frombooktobook.frombooktobookbackend.security.JwtUserDetails;
import com.frombooktobook.frombooktobookbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public User getCurrentUser(@CurrentUser JwtUserDetails userDetails) {
        return userService.getCurrentUser(userDetails.getId());
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {

    }

    @GetMapping("/existed-or-not")
    public ApiResponseDto checkIfExistEmail(@PathVariable String email) {
        if(userService.checkIfExistedEmail(email)) {
            return new ApiResponseDto(true, "email exists.");
        }else {
            return new ApiResponseDto(false, "email not exists.");
        }
    }
}
