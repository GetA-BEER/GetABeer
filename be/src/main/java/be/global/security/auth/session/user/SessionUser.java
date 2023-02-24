package be.global.security.auth.session.user;

import java.io.Serializable;

import be.domain.user.entity.User;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	private String act;
	private String rft;

	public SessionUser(String act, String rft) {
		this.act = act;
		this.rft = rft;
	}
}
