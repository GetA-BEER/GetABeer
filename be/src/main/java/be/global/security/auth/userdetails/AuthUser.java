package be.global.security.auth.userdetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import be.domain.user.entity.User;
import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;
import lombok.Getter;

@Getter
public class AuthUser extends User implements UserDetails {

	private Long id;
	private Age age;
	private List<String> roles;
	private String email;
	private Gender gender;
	private String nickname;
	private String password;
	private String provider;

	public AuthUser(User user) {
		this.id = user.getId();
		this.age = user.getAge();
		this.roles = user.getRoles();
		this.email = user.getEmail();
		this.gender = user.getGender();
		this.nickname = user.getNickname();
		this.password = user.getPassword();
		this.provider = user.getProvider();
	}

	public static AuthUser of(User user) {
		return new AuthUser(user);
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
}
