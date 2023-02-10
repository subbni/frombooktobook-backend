package com.frombooktobook.frombooktobookbackend.controller.user;
import com.frombooktobook.frombooktobookbackend.controller.auth.dto.ApiResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.user.dto.PasswordChangeRequestDto;
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

    @GetMapping("/existed-or-not/{email}")
    public ApiResponseDto checkIfExistEmail(@PathVariable String email) {
        if(userService.checkIfExistedEmail(email)) {
            return new ApiResponseDto(true, "email exists.");
        }else {
            return new ApiResponseDto(false, "email not exists.");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/password/change")
    public ApiResponseDto changePassword(@RequestBody PasswordChangeRequestDto requestDto) {
        if(!userService.checkIfPasswordIsCorrect(requestDto.getEmail(),requestDto.getCurrentPassword())) {
            return new ApiResponseDto(false,"비밀번호가 일치하지 않습니다.");
        }
        try{
            userService.changePassword(requestDto.getEmail(),requestDto.getNewPassword());
            return new ApiResponseDto(true,"비밀번호가 변경되었습니다.");
        }catch(Exception e) {
            return new ApiResponseDto(false,"비밀번호 변경에 실패하였습니다."+e.getMessage());
        }

    }
}
