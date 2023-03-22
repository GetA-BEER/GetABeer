package be.domain.comment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.domain.pairing.entity.Pairing;
import be.domain.user.entity.User;
import be.global.BaseTimeEntity;
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
public class PairingComment extends BaseTimeEntity {

	@Id
	@Column(name = "pairing_comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String content;

	/* 💚 페어링 댓글 - 페어링 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "pairing_id")
	private Pairing pairing;

	/* 💚 페어링 댓글 - 페어링 다대일 연관관계 편의 메서드 */
	public void belongToPairing(Pairing pairing) {
		this.pairing = pairing;
	}

	/* 💙페어링 댓글 - 회원 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/* 💙페어링 댓글 - 회원 다대일 연관관계 편의 메서드 */
	public void bndUser(User user) {
		this.user = user;
	}

	public void saveDefault (User user, Pairing pairing) {
		this.user = user;
		this.pairing = pairing;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
