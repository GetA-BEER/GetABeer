package be.global.security.auth.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import be.domain.user.entity.User;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

	private final UserRepository userRepository;
	private final CustomAuthorityUtils customAuthorityUtils;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

		return new UserDetailsImpl(user);
	}

	public class UserDetailsImpl extends User implements UserDetails {
		UserDetailsImpl(User user) {
			User.builder()
				.id(user.getId())
				// .age(user.getAge())
				.roles(user.getRoles())
				.email(user.getEmail())
				// .gender(user.getGender())
				.nickname(user.getNickname())
				.password(user.getPassword())
				.build();
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return customAuthorityUtils.createAuthorities(this.getRoles());
		}

		@Override
		public String getUsername() {
			return getEmail();
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
}
