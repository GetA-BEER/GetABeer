package be.domain.user.service.pattern;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import be.domain.user.entity.ProfileImage;
import be.domain.user.entity.User;
import be.global.image.ImageHandler;

public class EditImage implements StatePattern {

	@Override
	public ProfileImage click(StatePattern statePattern, StateButton stateButton, HashMap map, MultipartFile image,
		ImageHandler imageHandler,
		User user) throws IOException {
		ProfileImage profileImage = user.getProfileImage();

		map = imageHandler.updateProfileImage(image, "/profileImage", profileImage.getFileKey());

		return ProfileImage.builder()
			.id(profileImage.getId())
			.imageUrl(map.get("url").toString())
			.fileKey(map.get("fileKey").toString())
			.user(user)
			.build();
	}
}
