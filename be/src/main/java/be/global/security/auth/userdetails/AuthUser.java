package be.global.security.auth.userdetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import be.domain.user.entity.User;
import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;
import be.global.security.auth.oauth.strategy.userinfo.OAuth2UserInfo;
import lombok.Getter;

@Getter
public class AuthUser extends User implements UserDetails, OAuth2User {

	private Long id;
	private Age age;
	private List<String> roles;
	private String email;
	private Gender gender;
	private String nickname;
	private String password;
	private String provider;
	private OAuth2UserInfo oAuth2UserInfo;

	public AuthUser(Long id, Age age, List<String> roles, String email, Gender gender, String nickname, String password,
		String provider) {
		this.id = id;
		this.age = age;
		this.roles = roles;
		this.email = email;
		this.gender = gender;
		this.nickname = nickname;
		this.password = password;
		this.provider = provider;
	}

	public AuthUser(Long id, Age age, List<String> roles, String email, Gender gender, String nickname, String password,
		String provider, OAuth2UserInfo oAuth2UserInfo) {
		this.id = id;
		this.age = age;
		this.roles = roles;
		this.email = email;
		this.gender = gender;
		this.nickname = nickname;
		this.password = password;
		this.provider = provider;
		this.oAuth2UserInfo = oAuth2UserInfo;
	}

	public static AuthUser of(User user) {
		return new AuthUser(user.getId(), user.getAge(), user.getRoles(), user.getEmail(), user.getGender(),
			user.getNickname(), user.getPassword(), user.getProvider());
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oAuth2UserInfo.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(roles.get(0)));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getName() {
		return provider;
	}
}
