package be.domain.rating.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;

import be.domain.beer.entity.Beer;
import be.domain.comment.entity.RatingComment;
import be.domain.like.entity.RatingLike;
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
public class Rating extends BaseTimeEntity {

	@Id
	@Column(name = "beer_comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nickname;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Range(min = 0, max = 5)
	@Column(nullable = false)
	private Double star;

	@ColumnDefault("0")
	private Integer likeCount;

	@ColumnDefault("0")
	private Integer commentCount;

	/* 🤎 맥주 평가 - 회원 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/* 🤎 맥주 평가 - 회원 다대일 연관관계 편의 메서드 */
	public void bndUser(User user) {
		this.user = user;
	}

	/* 💛 맥주 평가 - 맥주 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "beer_id")
	private Beer beer;

	/* 💛 맥주 평가 - 맥주 다대일 연관관계 편의 메서드 */
	public void belongToBeer(Beer beer) {
		this.beer = beer;
	}

	@OneToOne(mappedBy = "rating", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private RatingTag ratingTag;

	public void oneToOneByRatingTag(RatingTag ratingTag) {
		this.ratingTag = ratingTag;

		if (ratingTag.getRating() != this) {
			ratingTag.oneToOneByRating(this);
		}
	}

	/* 💜 맥주 평가 - 맥주 댓글 일대다 연관관계 */
	@OneToMany(mappedBy = "rating", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<RatingComment> ratingCommentList = new ArrayList<>();

	/* 💜 맥주 평가 - 맥주 댓글 일대다 연관관계 편의 메서드 */
	public void addRatingCommentList(RatingComment ratingComment) {
		ratingCommentList.add(ratingComment);

		if (ratingComment.getRating() != this) {
			ratingComment.belongToRating(this);
		}
	}

	@OneToMany(mappedBy = "rating", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<RatingLike> ratingLikeList;

	public void addRatingLikeList(RatingLike ratingLike) {
		ratingLikeList.add(ratingLike);

		if (ratingLike.getRating() != this) {
			ratingLike.belongToRating(this);
		}
	}

	// ---------------------------------------------------------------------------------------------------------
	public void updateContent(String content) {
		this.content = content;
	}

	public void updateStar(Double star) {
		this.star = star;
	}

	public void saveDefault(Beer beer, User user, RatingTag ratingTag, String nickname,
		Integer likeCount, Integer commentCount, List<RatingComment> ratingCommentList) {
		this.beer = beer;
		this.user = user;
		this.nickname = nickname;
		this.ratingTag = ratingTag;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.ratingCommentList = ratingCommentList;
	}

	public void updateTag(RatingTag ratingTag) {
		this.ratingTag = ratingTag;
	}
	public void calculateComments(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public void calculateLikes(Integer likeCount) {
		this.likeCount = likeCount;
	}
}
