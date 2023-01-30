package be.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

    @Getter
    @Builder
    public static class Post {

        @NotBlank
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank
        private String nickname;

        @NotBlank
        private String password;

        private String image;
    }
}
