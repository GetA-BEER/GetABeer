package be.domain.user.service.pattern;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import be.domain.user.entity.ProfileImage;
import be.domain.user.entity.User;
import be.global.image.ImageHandler;

public interface StatePattern {
	ProfileImage click(StateButton stateButton, HashMap map, MultipartFile image, ImageHandler imageHandler,
		User user) throws IOException;
}

class FirstEditImage implements StatePattern {

	@Override
	public ProfileImage click(StateButton stateButton, HashMap map, MultipartFile image, ImageHandler imageHandler,
		User user) throws IOException {
		stateButton.setStatePattern(new EditImage());

		map = imageHandler.createProfileImage(image, "/profileImage");

		return ProfileImage.builder()
			.imageUrl(map.get("url").toString())
			.fileKey(map.get("fileKey").toString())
			.user(user)
			.build();
	}
}

class EditImage implements StatePattern {

	@Override
	public ProfileImage click(StateButton stateButton, HashMap map, MultipartFile image, ImageHandler imageHandler,
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
