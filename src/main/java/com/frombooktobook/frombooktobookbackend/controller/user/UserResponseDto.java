package com.frombooktobook.frombooktobookbackend.controller.user;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String name;
  //  private String password;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.name= user.getName();
   //     this.password = user.getPassword();
    }
}
