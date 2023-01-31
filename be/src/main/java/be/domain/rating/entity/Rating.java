package be.domain.rating.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;

import be.domain.beer.entity.Beer;
import be.domain.recomment.entity.BeerRecomment;
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

	@Column(nullable = false)
	private String nickname;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Range(min = 0, max = 5)
	@Column(nullable = false)
	private Double star;

	@ColumnDefault("0")
	private Integer likeCount;

	@ColumnDefault("0")
	private Integer recommentCount;

	/* 🤎 맥주 평가 - 회원 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/* 🤎 맥주 평가 - 회원 다대일 연관관계 편의 메서드 */
	public void belongToUser(User user) {
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

	/* 💜 맥주 평가 - 맥주 대댓글 일대다 연관관계 */
	@OneToMany(mappedBy = "beerComment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<BeerRecomment> beerRecommentList = new ArrayList<>();

	/* 💜 맥주 평가 - 맥주 대댓글 일대다 연관관계 편의 메서드 */
	public void addBeerRecommentList(BeerRecomment beerRecomment) {
		beerRecommentList.add(beerRecomment);

		if (beerRecomment.getBeerComment() != this) {
			beerRecomment.belongToBeerComment(this);
		}
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateStar(Double star) {
		this.star = star;
	}

	public void saveDefault(Integer likeCount, Integer recommentCount, List<BeerRecomment> beerRecommentList) {
		this.likeCount = likeCount;
		this.recommentCount = recommentCount;
		this.beerRecommentList = beerRecommentList;
	}
}
