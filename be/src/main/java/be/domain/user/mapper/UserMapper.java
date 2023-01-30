package be.domain.user.mapper;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default User postToUser(UserDto.Post post) {
        return User.builder()
                .email(post.getEmail())
                .password(post.getPassword())
                .nickname(post.getNickname())
                .build();
    }

    UserDto.Response userToResponse(User user);
}
