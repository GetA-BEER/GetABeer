package be.domain.user.service.pattern;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import be.domain.user.entity.ProfileImage;
import be.domain.user.entity.User;
import be.global.image.ImageHandler;

public class StateButton {
	private StatePattern statePattern = new FirstEditImage();

	public void setStatePattern(StatePattern statePattern) {
		this.statePattern = statePattern;
	}

	public ProfileImage clickButton(StateButton stateButton, HashMap map, MultipartFile image,
		ImageHandler imageHandler, User user) throws IOException {
		return statePattern.click(stateButton, map, image, imageHandler, user);
	}
}
