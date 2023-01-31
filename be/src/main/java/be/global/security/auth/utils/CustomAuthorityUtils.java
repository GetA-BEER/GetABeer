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

	private final List<GrantedAuthority> roleAdmin = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
	private final List<GrantedAuthority> roleUser = AuthorityUtils.createAuthorityList("ROLE_USER");

}
