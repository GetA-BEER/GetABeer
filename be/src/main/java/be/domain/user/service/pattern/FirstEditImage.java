package be.domain.user.service.pattern;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import be.domain.user.entity.ProfileImage;
import be.domain.user.entity.User;
import be.global.image.ImageHandler;

public class FirstEditImage implements StatePattern {

	@Override
	public ProfileImage click(StatePattern statePattern, StateButton stateButton, HashMap map, MultipartFile image,
		ImageHandler imageHandler,
		User user) throws IOException {

		map = imageHandler.createProfileImage(image, "/profileImage");

		return ProfileImage.builder()
			.imageUrl(map.get("url").toString())
			.fileKey(map.get("fileKey").toString())
			.user(user)
			.build();
	}
}
