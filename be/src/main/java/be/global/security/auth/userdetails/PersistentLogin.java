package be.global.security.auth.userdetails;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Getter
@Table(name = "persistent_login")
public class PersistentLogin {

	@Id
	@Column(length = 64)
	private String series;
	@Column(length = 64)
	private String email;
	@Column(length = 64)
	private String token;
	@Column(length = 64)
	private LocalDateTime lastUsed;
}
