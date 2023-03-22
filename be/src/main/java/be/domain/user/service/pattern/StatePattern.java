package be.domain.user.service.pattern;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import be.domain.user.entity.ProfileImage;
import be.domain.user.entity.User;
import be.global.image.ImageHandler;

public interface StatePattern {
	ProfileImage click(StatePattern statePattern, StateButton stateButton, HashMap map, MultipartFile image, ImageHandler imageHandler,
		User user) throws IOException;
}

