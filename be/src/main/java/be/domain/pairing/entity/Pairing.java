package be.domain.pairing.entity;

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

import org.hibernate.annotations.ColumnDefault;

import be.domain.beer.entity.Beer;
import be.domain.comment.entity.PairingComment;
import be.domain.like.entity.PairingLike;
import be.domain.pairing.dto.PairingImageDto;
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
public class Pairing extends BaseTimeEntity {

	@Id
	@Column(name = "paring_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Enumerated(EnumType.STRING)
	private PairingCategory pairingCategory;

	@ColumnDefault("0")
	private Integer likeCount;

	@ColumnDefault("0")
	private Integer commentCount;

	/* 🧡 페어링 - 페어링 이미지 일대다 연관관계 */
	@OneToMany(mappedBy = "pairing", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<PairingImage> pairingImageList;

	/* 🧡 페어링 - 페어링 이미지 일대다 연관관계 편의 메서드 */
	public void addPairingImageList(PairingImage pairingImage) {
		pairingImageList.add(pairingImage);

		if (pairingImage.getPairing() != this) {
			pairingImage.belongToPairing(this);
		}
	}

	/* 💙 페어링 - 맥주 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "beer_id")
	private Beer beer;

	/* 💙 페어링 - 맥주 다대일 연관관계 편의 메서드 */
	public void belongToBeer(Beer beer) {
		this.beer = beer;
	}

	/* 💚 페어링 - 페어링 댓글 일대다 연관관계 */
	@OneToMany(mappedBy = "pairing", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<PairingComment> pairingCommentList = new ArrayList<>();

	/* 💚 페어링 - 페어링 댓글 일대다 연관관계 편의 메서드 */
	public void addPairingCommentList(PairingComment pairingComment) {
		pairingCommentList.add(pairingComment);

		if (pairingComment.getPairing() != this) {
			pairingComment.belongToPairing(this);
		}
	}

	/* 🖤 페어링 - 회원 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/* 🖤 페어링 - 회원 다대일 연관관계 편의 메서드 */
	public void bndUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy = "pairing", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<PairingLike> pairingLikeList;

	public void addPairingLikeList(PairingLike pairingLike) {
		pairingLikeList.add(pairingLike);

		if (pairingLike.getPairing() != this) {
			pairingLike.belongToPairing(this);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	public void saveDefault(Beer beer, User user, List<PairingImage> pairingImageList,
		List<PairingComment> pairingCommentList,
		Integer likeCount, Integer commentCount) {
		this.beer = beer;
		this.user = user;
		this.pairingImageList = pairingImageList;
		this.pairingCommentList = pairingCommentList;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateCategory(PairingCategory pairingCategory) {
		this.pairingCategory = pairingCategory;
	}

	public void updateImageList(List<PairingImage> pairingImageList) {
		this.pairingImageList = pairingImageList;
	}

	public void calculateCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public void calculateLikes(Integer likeCount) {
		this.likeCount = likeCount;
	}
}
