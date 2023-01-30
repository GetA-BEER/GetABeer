package be.domain.user.mapper;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default Users postToUser(UserDto.Post post) {
        return Users.builder()
                .email(post.getEmail())
                .password(post.getPassword())
                .nickname(post.getNickname())
                .build();
    }

    UserDto.Response userToResponse(Users users);
}
