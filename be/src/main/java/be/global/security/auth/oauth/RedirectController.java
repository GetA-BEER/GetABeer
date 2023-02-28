package be.global.security.auth.oauth;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RedirectController {
	private final UserMapper userMapper;
	private final UserService userService;

	@GetMapping("/api/token")
	@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
	public ResponseEntity redirect(@RequestParam("access_token") String act,
		@RequestParam("refresh_token") String rft) throws URISyntaxException {

		// URI redirect = new URI("https://www.getabeer.co.kr");
		URI redirect = new URI("http://localhost:3000");

		ResponseCookie cookie = ResponseCookie.from("refreshToken", rft)
			.maxAge(7 * 24 * 60 * 60)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(redirect);
		headers.set("Set-Cookie", cookie.toString());
		headers.set("Authorization", act);

		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
}
