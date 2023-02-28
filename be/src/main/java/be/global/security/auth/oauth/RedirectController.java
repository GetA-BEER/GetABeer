package be.global.security.auth.oauth;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {

	@GetMapping("/api/token")
	public ResponseEntity redirect(@RequestParam("user_id") String userId,
		@RequestParam("access_token") String act,
		@RequestParam("refresh_token") String rft) throws URISyntaxException {

		URI redirect = new URI(
			"https://www.getabeer.co.kr/signup/information?"
				+ "user_id=" + userId
				+ "&access_token=" + act
				+ "&refresh_token=" + rft);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(redirect);

		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
}
