package be.global.security.oauth.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import be.domain.user.entity.User;
import be.domain.user.entity.enums.UserStatus;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleService {
	private String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String GOOGLE_CLIENT_ID;
	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String GOOGLE_CLIENT_SECRET;
	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String GOOGLE_REDIRECT_URL;
	private String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

	private final UserRepository userRepository;
	private final CustomAuthorityUtils customAuthorityUtils;
	private final PasswordEncoder passwordEncoder;

	public User getAccessToken(String code) {

		String accessToken = "";
		String refreshToken = "";

		try {
			URL url = new URL(GOOGLE_TOKEN_URL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=").append(GOOGLE_CLIENT_ID);
			sb.append("&client_secret=").append(GOOGLE_CLIENT_SECRET);
			sb.append("&code=").append(code);
			sb.append("&redirect_uri=").append(GOOGLE_REDIRECT_URL);
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder result = new StringBuilder();
			String line = "";

			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonElement element = JsonParser.parseString(result.toString());

			accessToken = element.getAsJsonObject().get("access_token").getAsString();
			refreshToken = element.getAsJsonObject().get("id_token").getAsString();

			System.out.println("access_token : " + accessToken);
			System.out.println("refresh_token : " + refreshToken);

			br.close();
			bw.close();

		} catch (IOException e) {
			throw new IllegalArgumentException(GOOGLE_TOKEN_URL);
		}
		return getUserInfo(accessToken);
		// return "구글 로그인 요청 처리 실패";
	}

	public User getUserInfo(String token) {
		HashMap<String, Object> userInfo = new HashMap<>();
		try {
			URL url = new URL(GOOGLE_USER_INFO_URL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Authorization", "Bearer " + token);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			if (conn.getResponseCode() == 200) {
				JsonElement element = JsonParser.parseString(sb.toString());

				String providerId = element.getAsJsonObject().get("id").getAsString();
				String nickname = element.getAsJsonObject().get("name").getAsString();
				String email = element.getAsJsonObject().get("email").getAsString();
				String profileImage = element.getAsJsonObject().get("picture").getAsString();

				userInfo.put("providerId", providerId);
				userInfo.put("nickname", nickname);
				userInfo.put("email", email);
				userInfo.put("profileImage", profileImage);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("알 수 없는 구글 User Info 요청 URL 입니다 :: " + GOOGLE_USER_INFO_URL);
		}

		return createUser(userInfo);
	}

	public User createUser(HashMap<String, Object> userInfo) {

		String providerId = userInfo.get("providerId").toString();
		String email = userInfo.get("email").toString();
		String picture = userInfo.get("profileImage").toString();
		String nickname = userInfo.get("nickname").toString();
		String encodedPass = passwordEncoder.encode(userInfo.get("nickname").toString());

		User findUser = userRepository.findByProviderId(providerId);

		if (findUser != null && findUser.getProvider().equals("GOOGLE")) {
			findUser.updateOAuthInfo(picture, nickname);
			return userRepository.save(findUser);
		} else if (findUser == null && userRepository.findByEmail(email).isPresent()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_USED_ANOTHER_ACCOUNT);
		}

		User.UserBuilder userBuilder = User.builder();

		userBuilder.email(email);

		if (userRepository.findByNickname(nickname) == null) {
			userBuilder.nickname(nickname);
		} else {
			String rand = UUID.randomUUID().toString().substring(0, 6);
			userBuilder.nickname(nickname + "_GOOGLE_" + rand);
		}

		userBuilder.status(UserStatus.ACTIVE_USER.getStatus());
		userBuilder.imageUrl(picture);
		userBuilder.roles(customAuthorityUtils.createRoles(email));
		userBuilder.password(encodedPass);
		userBuilder.provider("GOOGLE");
		userBuilder.providerId(providerId);

		return userRepository.save(userBuilder.build());
	}
}
