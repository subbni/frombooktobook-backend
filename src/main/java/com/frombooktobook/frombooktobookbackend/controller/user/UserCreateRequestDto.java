package com.frombooktobook.frombooktobookbackend.controller.user;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {
   private String email;
   private String name;


   @Builder
   public UserCreateRequestDto(String email, String name) {
      this.email=email;
      this.name=name;
   }

   public User toEntity() {
      return User.builder()
              .email(email)
              .name(name)
              .build();
   }

}
