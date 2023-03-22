package be.domain.user.service.pattern;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import be.domain.user.entity.ProfileImage;
import be.domain.user.entity.User;
import be.global.image.ImageHandler;

public class StateButton {
	public StatePattern statePattern;

	public void setStatePattern(StatePattern statePattern) {
		this.statePattern = statePattern;
	}

	public ProfileImage clickButton(StatePattern statePattern, StateButton stateButton, HashMap map, MultipartFile image,
		ImageHandler imageHandler, User user) throws IOException {
		stateButton.setStatePattern(statePattern);
		return statePattern.click(statePattern, stateButton, map, image, imageHandler, user);
	}
}
