package be.domain.like.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.domain.pairing.entity.Pairing;
import be.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PairingLike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private LikeStatus pairingLikeStatus;

	/* 📍 페어링 추천 - 회원 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public void belongToUser(User user) {
		this.user = user;
	}

	/* 📍 페어링 추천 - 페어링 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "pairing_id")
	private Pairing pairing;

	public void belongToPairing(Pairing pairing) {
		this.pairing = pairing;
	}
}
