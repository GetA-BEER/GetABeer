package be.domain.comment.entity;

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
import javax.validation.constraints.Max;

import org.hibernate.annotations.ColumnDefault;

import be.domain.beer.entity.Beer;
import be.domain.recomment.entity.BeerRecomment;
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
public class BeerComment extends BaseTimeEntity {

	@Id
	@Column(name = "beer_comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickname;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Max(5)
	@Column(nullable = false)
	private Double star;

	@ColumnDefault("0")
	private Integer likeCount;

	@ColumnDefault("0")
	private Integer recommentCount;

	/* 💛 맥주 코멘트 - 맥주 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "beer_id")
	private Beer beer;

	/* 💛 맥주 코멘트 - 맥주 다대일 연관관계 편의 메서드 */
	public void belongToBeer(Beer beer) {
		this.beer = beer;
	}

	/* 💜 맥주 댓글 - 맥주 대댓글 일대다 연관관계 */
	@OneToMany(mappedBy = "beerComment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<BeerRecomment> beerRecommentList = new ArrayList<>();

	/* 💜 맥주 댓글 - 맥주 대댓글 일대다 연관관계 편의 메서드 */
	public void addBeerRecommentList(BeerRecomment beerRecomment) {
		beerRecommentList.add(beerRecomment);

		if (beerRecomment.getBeerComment() != this) {
			beerRecomment.belongToBeerComment(this);
		}
	}
}
