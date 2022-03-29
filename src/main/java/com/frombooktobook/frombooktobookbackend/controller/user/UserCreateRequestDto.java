package com.frombooktobook.frombooktobookbackend.controller.user;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {
   private String email;
   private String nickname;
   private String password;

   @Builder
   public UserCreateRequestDto(String email, String nickname, String password) {
      this.email=email;
      this.nickname=nickname;
      this.password= password;
   }

   public User toEntity() {
      return User.builder()
              .email(email)
              .nickname(nickname)
              .password(password)
              .build();
   }

}
