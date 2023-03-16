package be.global.security.auth.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class CustomAuthorityUtils {

	@Getter
	@Value("${mail.address.admin}")
	private String adminMailAddress;

	private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
	private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
	private final List<String> ADMIN_ROLES_STRING = List.of("ROLE_ADMIN", "ROLE_USER");
	private final List<String> USER_ROLES_STRING = List.of("ROLE_USER");

	public List<GrantedAuthority> createAuthorities(String email) {
		if (email.equals(adminMailAddress)) {
			return ADMIN_ROLES;
		}
		return USER_ROLES;
	}

	public List<GrantedAuthority> createAuthorities(List<String> roles) {
		return roles.stream()
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
			.collect(Collectors.toList());
	}

	public List<String> createRoles(String email) {
		if (email.equals(adminMailAddress)) {
			return ADMIN_ROLES_STRING;
		}

		return USER_ROLES_STRING;
	}
}
