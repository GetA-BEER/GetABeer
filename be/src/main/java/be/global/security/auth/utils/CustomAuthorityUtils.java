package be.global.security.auth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthorityUtils {

	@Value("${mail.address.admin}")
	private String adminMailAddress;

	private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
	private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
	private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
	private final List<String> USER_ROLES_STRING = List.of("USER");

}
