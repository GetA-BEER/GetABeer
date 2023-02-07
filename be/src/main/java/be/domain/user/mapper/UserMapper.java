package be.domain.user.mapper;

import org.mapstruct.Mapper;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	default User postToUser(UserDto.RegisterPost post) {
		return User.builder()
			.email(post.getEmail())
			.password(post.getPassword())
			.nickname(post.getNickname())
			.build();
	}

	UserDto.Response userToResponse(User user);
}
