package be.domain.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*?\\d)(?=.*?[#?!@$%^&*-]).{8,}$",
                message = "비밀번호는 8자 이상 특수문자와 영어 대소문자, 숫자만 허용됩니다.")
        private String password;
    }

    @Getter
    @Builder
    public static class Patch {

        @NotBlank
        private String email;

        @NotBlank
        private String nickname;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private String email;
        private String nickname;
        private String image;
    }

    @Getter
    public static class Login {

        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }
}
