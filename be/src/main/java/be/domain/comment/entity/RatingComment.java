package be.domain.comment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.domain.rating.entity.Rating;
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
public class RatingComment extends BaseTimeEntity {

	@Id
	@Column(name = "rating_comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String content;

	/* 💜 맥주 댓글 - 맥주 평가 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "rating_id")
	private Rating rating;

	/* 💜 맥주 댓글 - 맥주 평가 다대일 연관관계 편의 메서드 */
	public void belongToRating(Rating rating) {
		this.rating = rating;
	}

	/* 💝 맥주 댓글 - 회원 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public void bndUser(User user) {
		this.user = user;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void saveDefault(Rating rating) {
		this.rating = rating;
	}
}
