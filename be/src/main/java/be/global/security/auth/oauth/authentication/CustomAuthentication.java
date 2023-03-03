package be.global.security.auth.oauth.authentication;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import be.domain.user.entity.User;

public class CustomAuthentication implements Authentication {

	private User user;

	public CustomAuthentication(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>(user.getRoles().size());

		for (String role : user.getRoles())
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

		return authorities;
	}

	@Override
	public Object getCredentials() {
		return user.getId();
	}

	@Override
	public Object getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

	}

	@Override
	public String getName() {
		return user.getNickname();
	}
}
